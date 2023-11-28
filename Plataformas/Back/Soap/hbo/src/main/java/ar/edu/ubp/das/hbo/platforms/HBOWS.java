package ar.edu.ubp.das.hbo.platforms;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.cxf.interceptor.Fault;

import com.google.gson.Gson;

import ar.edu.ubp.das.hbo.beans.ActuacionCatalogo;
import ar.edu.ubp.das.hbo.beans.Catalogo;
import ar.edu.ubp.das.hbo.beans.Codigo;
import ar.edu.ubp.das.hbo.beans.ContenidoCatalogo;
import ar.edu.ubp.das.hbo.beans.DireccionCatalogo;
import ar.edu.ubp.das.hbo.beans.RespuestaBean;
import ar.edu.ubp.das.hbo.utils.TokenGenerator;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@WebService
@XmlSeeAlso({RespuestaBean.class})
public class HBOWS {
    private String driver_sql;
    private String sql_conection_string;
    private String sql_user;
    private String sql_pass;
    private RespuestaBean respuesta;
    
    public HBOWS() throws Fault{
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")){
            properties.load(input);
            driver_sql = properties.getProperty("driver_sql");
            sql_conection_string = properties.getProperty("sql_conection_string");
            sql_user = properties.getProperty("sql_user");
            sql_pass = properties.getProperty("sql_pass");
            Class.forName(driver_sql);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    @WebMethod()
    @WebResult(name = "respuesta")
    public RespuestaBean obtenerLoginUrl(@WebParam(name = "url_retorno") String url_retorno, @WebParam(name = "token_servicio") String token_servicio){
        if (!tokenValid(token_servicio)){
            return handleUnauthorizedResponse("El token de servicio proporcionado no es válido");
        }
        try (Connection conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass)) {
            String transaction_id = TokenGenerator.generateTransactionID();
            CallableStatement stmt;
            conn.setAutoCommit(true);
            stmt = conn.prepareCall("{CALL dbo.iniciar_login(?, ?, ?, ?)}");

            stmt.setString(1, token_servicio);
            stmt.setString(2, transaction_id);
            stmt.setString(3, url_retorno);
            stmt.registerOutParameter(4, java.sql.Types.INTEGER);

            stmt.execute();

            int id_login = stmt.getInt(4);
            
            if (id_login == 0){
                respuesta.setStatus(Codigo.ERROR);
                respuesta.setBody("Id login = 0. Error en inserción SQL");
                respuesta.setMensaje("Error en la obtención de la url");
                return respuesta;
            }

            stmt.close();
            conn.close();

            String URL = "http://localhost:8094/hbo/login?id=" + Integer.toString(id_login);
            Map<String, String> data = new HashMap<String, String>();
            data.put("transaction_id", transaction_id);
            data.put("URL", URL);

            respuesta.setStatus(Codigo.OK);
            respuesta.setBody(new Gson().toJson(data));
            respuesta.setMensaje("Url obtenida con éxito");
            return respuesta;
        } catch (Exception e) {
            return handleErrorResponse("Error en la obtención de la url", e);
        }
    }

    @WebMethod()
    @WebResult(name = "respuesta")
    public RespuestaBean obtenerTokenViewer(@WebParam(name = "transaction_id") String transaction_id, @WebParam(name = "token_servicio") String token_servicio){
        if (!tokenValid(token_servicio)){
            return handleUnauthorizedResponse("El token de servicio proporcionado no es válido");
        }
        try (Connection conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass)) {
            CallableStatement stmt;
            conn.setAutoCommit(true);

            stmt = conn.prepareCall("{CALL dbo.obtener_viewer_token(?, ?, ?)}");
            stmt.setString(1, transaction_id);
            stmt.setString(2, token_servicio);
            stmt.registerOutParameter(3, java.sql.Types.VARCHAR);

            stmt.execute();

            String token = stmt.getString(3);

            if (token == null){
                return handleErrorResponse("Error en la obtención del token", new Exception("ERROR SQL"));
            }
            
            Map<String, String> data = new HashMap<String, String>();
            data.put("token", token);
            respuesta.setBody(new Gson().toJson(data));
            respuesta.setMensaje("Token de viewer obtenido con éxito");
            respuesta.setStatus(Codigo.OK);
            stmt.close();
            return respuesta;
        } catch (Exception e) {
            return handleErrorResponse("Error en la obtención del token", e);
        }
    }

    @WebMethod()
    @WebResult(name = "respuesta")
    public RespuestaBean esUsuarioNuevo(@WebParam(name = "transaction_id") String transaction_id, @WebParam(name = "token_servicio") String token_servicio){
        if (!tokenValid(token_servicio)){
            return handleUnauthorizedResponse("El token de servicio proporcionado no es válido");
        }
        try (Connection conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass)){
            CallableStatement stmt;
            conn.setAutoCommit(true);
            stmt = conn.prepareCall("{CALL dbo.viewer_nuevo(?, ?, ?)}");
            stmt.setString(1, transaction_id);
            stmt.registerOutParameter(2, java.sql.Types.BIT);
            stmt.registerOutParameter(3, java.sql.Types.BIT);

            stmt.execute();

            boolean usuario_logueado = stmt.getBoolean(2);
            if (!usuario_logueado){        
                respuesta.setStatus(Codigo.ERROR);
                respuesta.setBody("ERROR");
                respuesta.setMensaje("El login asociado al transaction_id proporcionado no se ha completado");
                return respuesta;
            }
            boolean es_nuevo = stmt.getBoolean(3);

            respuesta.setBody(new Gson().toJson(es_nuevo));
            respuesta.setMensaje("Dato obtenido con éxito");
            respuesta.setStatus(Codigo.OK);

            conn.close();
            stmt.close();
            return respuesta;
        } catch (Exception e) {
            return handleErrorResponse("Error en la obtención de los datos", e);
        }
    }

    @WebMethod()
    @WebResult(name = "respuesta")
    public RespuestaBean obtenerCatalogo(@WebParam(name = "sesion") String sesion, @WebParam(name = "token_servicio") String token_servicio){
        if (!tokenValid(token_servicio)){
            return handleUnauthorizedResponse("El token de servicio proporcionado no es válido");
        }
        respuesta = validarSesion(sesion, token_servicio);
        if (respuesta.getStatus() != Codigo.OK)
            return respuesta;
        try (Connection conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass)){
            Catalogo catalogo = new Catalogo();
            conn.setAutoCommit(true);
            obtenerContenidos(conn, catalogo);
            obtenerDirecciones(conn, catalogo);
            obtenerActuaciones(conn, catalogo);
            usarSesion(conn, sesion);

            respuesta.setStatus(Codigo.OK);
            respuesta.setMensaje("Catálogo obtenido con éxito");
            respuesta.setBody(new Gson().toJson(catalogo));
            return respuesta;
        } catch (Exception e) {
            return handleErrorResponse("Error en la obtención del catálogo", e);
        }
    }

    @WebMethod()
    @WebResult(name = "respuesta")
    public RespuestaBean obtenerSesion(@WebParam(name = "token_viewer") String token_viewer, @WebParam(name = "token_servicio") String token_servicio){
        if (!tokenValid(token_servicio)){
            return handleUnauthorizedResponse("El token de servicio proporcionado no es válido");
        }
        if (!viwerTokenValid(token_viewer, token_servicio)){
            return handleUnauthorizedResponse("El token de viewer utilizado no es válido");
        }
        try (Connection conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass)){
            conn.setAutoCommit(true);
            try (CallableStatement stmt = conn.prepareCall("{CALL dbo.obtener_sesion(?, ?, ?)}")){
                stmt.setString(1, token_viewer);
                stmt.setString(2, token_servicio);
                stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
                stmt.execute();
                String sesion = stmt.getString(3);
                if (sesion == null){
                    return handleErrorResponse("Error al obtener la sesión", new Exception("Error SQL"));
                }
                Map<String, String> data = new HashMap<String, String>();
                data.put("sesion", sesion);
                return new RespuestaBean(Codigo.OK, "Sesion obtenida con éxito", new Gson().toJson(data));
            }
        } catch (Exception e) {
            return handleErrorResponse("Error en la obtención de la sesión", e);
        }
    }

    @WebMethod()
    @WebResult(name = "respuesta")
    public RespuestaBean obtenerUrlContenido(@WebParam(name = "sesion") String sesion, 
                                                @WebParam(name = "eidr_contenido") String eidr_contenido, 
                                                @WebParam(name = "token_servicio") String token_servicio){
        if (!tokenValid(token_servicio)){
            return handleUnauthorizedResponse("El token de servicio proporcionado no es válido");
        }
        respuesta = validarSesion(sesion, token_servicio);
        if (respuesta.getStatus() != Codigo.OK)
            return respuesta;
        try (Connection conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass)){
            conn.setAutoCommit(true);
            CallableStatement stmt = conn.prepareCall("{CALL dbo.obtener_contenido(?)}");
            stmt.setString(1, eidr_contenido);
            ResultSet rs = stmt.executeQuery();
            Map<String,String> urls = new HashMap<String,String>();
            while (rs.next()) {
                urls.put("url"+rs.getRow() , rs.getString("url_contenido"));
            }
            return new RespuestaBean(Codigo.OK, "Datos obtenidos con éxito", new Gson().toJson(urls));
        } catch (Exception e) {
            return handleErrorResponse("Error en la obtención de la url de contenido", e);
        }
    }

    //LOGIN
    @WebMethod()
    @WebResult(name = "validacion")
    public RespuestaBean login(@WebParam(name = "id_login") int id_login, 
                                @WebParam(name = "email") String email, 
                                @WebParam(name = "password") String password){
        respuesta = validarIdLogin(id_login);
        if (respuesta.getStatus() != Codigo.OK)
            return respuesta;
        int id_servicio = Integer.parseInt(respuesta.getBody());
        return nuevoLogin(id_login, email, password, id_servicio, false);
    }

    //REGISTRAR
    @WebMethod()
    @WebResult(name = "validacion")
    public RespuestaBean register(@WebParam(name = "id_login") int id_login,
                                    @WebParam(name = "nombres") String nombres,
                                    @WebParam(name = "apellidos") String apellidos,
                                    @WebParam(name = "email") String email,
                                    @WebParam(name = "password") String password){
        respuesta = validarIdLogin(id_login);
        if (respuesta.getStatus() != Codigo.OK)
            return respuesta;
        int id_servicio = Integer.parseInt(respuesta.getBody());
        int id_viewer = 0;
        try (Connection conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass)){
            conn.setAutoCommit(true);
            try (CallableStatement stmt = conn.prepareCall("{CALL dbo.insertar_viewer(?, ?, ?, ?, ?)}")){
                stmt.setString(1, nombres);
                stmt.setString(2, apellidos);
                stmt.setString(3, email);
                stmt.setString(4, password);
                stmt.registerOutParameter(5, java.sql.Types.INTEGER);
                stmt.execute();
                id_viewer = stmt.getInt(5);
                if (id_viewer == 0)
                    return handleErrorResponse("Error al insertar el nuevo usuario", new Exception("ERROR SQL"));
            }

            return nuevoLogin(id_login, email, password, id_servicio, true);

        } catch (Exception e) {
            return handleErrorResponse("Error al registrar el nuevo usuario", e);
        }
    }
    /*-------------------------------------------------------------------------------------------------------*/

    private boolean tokenValid(String token_servicio){
        try (Connection conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass)){
            conn.setAutoCommit(true);
            try (CallableStatement stmt = conn.prepareCall("{CALL dbo.validar_token_servicio(?, ?)}")){
                stmt.setString(1, token_servicio);
                stmt.registerOutParameter(2, java.sql.Types.BIT); 

                stmt.execute();

                boolean resultado = stmt.getBoolean(2);
                return resultado;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private void usarSesion(Connection conn, String sesion) throws SQLException {
        try (CallableStatement stmt = conn.prepareCall("{CALL dbo.usar_sesion(?)}")) {
            stmt.setString(1, sesion);
            stmt.execute();
        }
    }

    private void obtenerDirecciones(Connection conn, Catalogo catalogo) throws SQLException{
        try (CallableStatement stmt = conn.prepareCall("{CALL dbo.obtener_direcciones()}")){
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    catalogo.addDireccion(new DireccionCatalogo(
                        rs.getString("eidr_Contenido"),
                        rs.getString("nombres"),
                        rs.getString("apellidos")
                    ));
                }
            }
        }
    }

    private void obtenerActuaciones(Connection conn, Catalogo catalogo) throws SQLException{
        try (CallableStatement stmt = conn.prepareCall("{CALL dbo.obtener_actuaciones()}")){
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    catalogo.addActuacion(new ActuacionCatalogo(
                        rs.getString("eidr_Contenido"),
                        rs.getString("nombres"),
                        rs.getString("apellidos")
                    ));
                }
            }
        }
    }

    private void obtenerContenidos(Connection conn, Catalogo catalogo) throws SQLException{
        try (CallableStatement stmt = conn.prepareCall("{CALL dbo.obtener_contenidos()}")){
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    catalogo.addContenido(new ContenidoCatalogo(
                        rs.getString("edir_contenido"),
                        rs.getString("titulo"),
                        rs.getString("url_imagen"),
                        rs.getString("descripcion"),
                        rs.getDate("fecha_estreno"),
                        rs.getString("genero"),
                        rs.getString("pais"),
                        rs.getString("tipo_contenido")
                    ));
                }
            }
        }
    }

    private RespuestaBean validarSesion(String sesion, String token_servicio){
        try (Connection conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass)){
            conn.setAutoCommit(true);
            try (CallableStatement stmt = conn.prepareCall("{CALL dbo.sesion_valida(?, ?, ?)}")){
                stmt.setString(1, sesion);
                stmt.setString(2, token_servicio);
                stmt.registerOutParameter(3, java.sql.Types.INTEGER);
                stmt.execute();
                int resultado = stmt.getInt(3);
                switch (resultado) {
                    case 1:
                        return new RespuestaBean(Codigo.OK, "Sesión válida", "Sesión válida");
                    case 2:
                        return new RespuestaBean(Codigo.NO_AUTORIZADO, "La sesión ya ha sido utilizada", "Sesion usada");
                    case 3:
                        return new RespuestaBean(Codigo.NO_AUTORIZADO, "La sesión ya se encuentra vencida", "Sesion vencida");
                    case 4:
                        return new RespuestaBean(Codigo.NO_AUTORIZADO, "La sesion ha sido marcada como inhabilitada", "Sesion dada de baja");
                    case 5:
                        return new RespuestaBean(Codigo.NO_ENCONTRADO, "La sesión no se ha encontrado en la base de datos", "Sesión inexistente");
                    default:
                        return new RespuestaBean(Codigo.ERROR, "Error al intentar obtener el estado de la sesión", "ERROR");
                }
            }
        }
        catch (SQLException e){
            return handleErrorResponse("Error al validar la sesión", e);
        }
    }
    
    private RespuestaBean handleUnauthorizedResponse(String mensaje) {
        return new RespuestaBean(Codigo.NO_AUTORIZADO, mensaje, "NO AUTORIZADO");
    }

    private RespuestaBean handleErrorResponse(String mensaje, Exception e){
        return new RespuestaBean(Codigo.ERROR, mensaje, e.toString());
    }

    private boolean viwerTokenValid(String viewer_token, String token_servicio){
        try (Connection conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass)){
            conn.setAutoCommit(true);
            try (CallableStatement stmt = conn.prepareCall("{CALL dbo.validar_token_viewer(?, ?, ?)}")){
                stmt.setString(1, viewer_token);
                stmt.setString(2, token_servicio);
                stmt.registerOutParameter(3, java.sql.Types.BIT);
                stmt.execute();
                boolean resultado = stmt.getBoolean(3);
                return resultado;
            }
        } catch (Exception e){
            return false;
        }
    }

    private RespuestaBean validarIdLogin(int id_login){
        try (Connection conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass)){
            conn.setAutoCommit(true);
            try (CallableStatement stmt = conn.prepareCall("{CALL dbo.validar_id_login(?, ?, ?)}")){
                stmt.setInt(1, id_login);
                stmt.registerOutParameter(2, java.sql.Types.BIT);
                stmt.registerOutParameter(3, java.sql.Types.INTEGER);
                stmt.execute();
                boolean valido = stmt.getBoolean(2);
                if (!valido)
                    return handleUnauthorizedResponse("El id de login no es válido");
                int id_servicio = stmt.getInt(3);
                return new RespuestaBean(Codigo.OK, "Id login valido", Integer.toString(id_servicio));
            }
        } catch (Exception e) {
            return handleErrorResponse("Error sql al validar el id_login", e);
        }
    }

    private RespuestaBean nuevoLogin(int id_login, String email, String password, int id_servicio, boolean viewer_nuevo){
        try (Connection conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass)){
            conn.setAutoCommit(true);
            try (CallableStatement stmt = conn.prepareCall("{CALL dbo.login_viewer(?, ?, ?, ?, ?, ?, ?)}")){
                String token = TokenGenerator.generateToken(Integer.toString(id_login)+email+password+Integer.toString(id_servicio));
                stmt.setInt(1, id_login);
                stmt.setString(2, email);
                stmt.setString(3, password);
                stmt.setString(4, token);
                stmt.setInt(5, id_servicio);
                stmt.setBoolean(7, viewer_nuevo);
                stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
                stmt.execute();
                String url_retorno = stmt.getString("url_retorno");
                if (url_retorno == null || url_retorno.isEmpty())
                    return handleUnauthorizedResponse("El email y/o contraseña no son válidos");
                Map<String, String> data = new HashMap<String, String>();
                data.put("url_retorno", url_retorno);
                return new RespuestaBean(Codigo.OK, "Login correcto", new Gson().toJson(data));
            }
        } catch (Exception e) {
            return handleErrorResponse("Error al intentar realizar un login", e);
        }
    }
}
