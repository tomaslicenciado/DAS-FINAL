package ar.edu.ubp.das.netflix.repositories;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ar.edu.ubp.das.netflix.beans.ActuacionCatalogo;
import ar.edu.ubp.das.netflix.beans.Catalogo;
import ar.edu.ubp.das.netflix.beans.Codigo;
import ar.edu.ubp.das.netflix.beans.ContenidoCatalogo;
import ar.edu.ubp.das.netflix.beans.DireccionCatalogo;
import ar.edu.ubp.das.netflix.beans.RespuestaBean;
import ar.edu.ubp.das.netflix.utils.TokenGenerator;

@Repository
public class NetflixRepository implements INetflixRepository {
    @Autowired
    private JdbcTemplate jdbcTpl;

    private SqlParameterSource in;
    private SimpleJdbcCall jdbcCall;
    private Map<String, Object> out;
    private Gson gson;

    NetflixRepository(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd-MM-yyyy");
        gson = gsonBuilder.create();
    }

    @Override
    public RespuestaBean obtenerLoginUrl(String url_retorno, String token_servicio) {
        if (!tokenValid(token_servicio))
            return handleUnauthorizedResponse("El token de servicio proporcionado no es válido");
        try {
            String transaction_id = TokenGenerator.generateTransactionID();
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("iniciar_login").withSchemaName("dbo");
            in = new MapSqlParameterSource().addValue("token_servicio", token_servicio)
                                            .addValue("transaction_id", transaction_id)
                                            .addValue("url_retorno", url_retorno)
                                            .addValue("id_login", null, Types.INTEGER);
            out = jdbcCall.execute(in);
            if (!out.containsKey("id_login"))
                throw new Exception("ERROR SQL");
            int id_login = (int)out.get("id_login");
            if (id_login == 0)
                throw new Exception("Id login = 0. Error en inserción SQL");
            String URL = "http://localhost:8091/netflix/login?id=" + Integer.toString(id_login);
            Map<String, String> data = new HashMap<>();
            data.put("transaction_id", transaction_id);
            data.put("URL", URL);
            return new RespuestaBean(Codigo.OK, "Url obtenida con éxito", gson.toJson(data));
        } catch (Exception e) {
            return handleErrorResponse("Error en la obtención de la url de login", e);
        }
    }

    @Override
    public RespuestaBean obtenerTokenViewer(String transaction_id, String token_servicio) {
        if (!tokenValid(token_servicio))
            return handleUnauthorizedResponse("El token de servicio proporcionado no es válido");
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("obtener_viewer_token").withSchemaName("dbo");
            in = new MapSqlParameterSource().addValue("transaction_id", transaction_id).addValue("token_servicio", token_servicio)
                            .addValue("viewer_token", null, Types.VARCHAR);
            out = jdbcCall.execute(in);
            if (!out.containsKey("viewer_token"))
                throw new Exception("ERROR SQL");
            String viewer_token = (String)out.get("viewer_token");
            if (viewer_token == null || viewer_token.isEmpty())
                return handleUnauthorizedResponse("Los datos proporcionados no tienen un token de viewer asociado");
            Map<String, String> data = new HashMap<>();
            data.put("token", viewer_token);
            return new RespuestaBean(Codigo.OK, "Token viewer obtenido con éxito", gson.toJson(data));
        } catch (Exception e) {
            return handleErrorResponse("Error en la obtención del token de viewer", e);
        }
    }

    @Override
    public RespuestaBean esUsuarioNuevo(String transaction_id, String token_servicio) {
        if (!tokenValid(token_servicio))
            return handleUnauthorizedResponse("El token de servicio proporcionado no es válido");
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withSchemaName("dbo").withProcedureName("viewer_nuevo");
            in = new MapSqlParameterSource().addValue("transaction_id", transaction_id)
                    .addValue("token_servicio", token_servicio).addValue("login_completo", null, Types.BIT)
                    .addValue("resultado", null, Types.BIT);
            out = jdbcCall.execute(in);
            if (!out.containsKey("resultado") || !out.containsKey("login_completo"))
                throw new Exception("ERROR SQL");
            boolean nuevo = (boolean)out.get("resultado");
            boolean login_completo = (boolean)out.get("login_completo");
            if (!login_completo)
                return new RespuestaBean(Codigo.NO_ENCONTRADO, "El transaction id no tiene un viewer asociado", 
                            "El transaction id corresponde a un proceso de login incompleto");
            return new RespuestaBean(Codigo.OK, "Dato obtenido con éxito", gson.toJson(nuevo));
        } catch (Exception e) {
            return handleErrorResponse("Error en la obtención delos datos del viewer", e);
        }
    }

    @Override
    public RespuestaBean obtenerCatalogo(String sesion, String token_servicio) {
        if (!tokenValid(token_servicio))
            return handleUnauthorizedResponse("El token de servicio proporcionado no es válido");
        RespuestaBean valid_sesion = validarSesion(sesion, token_servicio);
        if (valid_sesion.getStatus() != Codigo.OK)
            return valid_sesion;
        try {
            Catalogo catalogo = new Catalogo();
            obtenerActuaciones(catalogo);
            obtenerContenidos(catalogo);
            obtenerDirecciones(catalogo);
            usarSesion(sesion);
            return new RespuestaBean(Codigo.OK, "Catálogo obtenido con éxito",gson.toJson(catalogo));
        } catch (Exception e) {
            return handleErrorResponse("Error en la obtención del catálogo", e);
        }
    }

    @Override
    public RespuestaBean obtenerSesion(String token_viewer, String token_servicio) {
        if (!tokenValid(token_servicio))
            return handleUnauthorizedResponse("El token de servicio proporcionado no es válido");
        if (!viwerTokenValid(token_viewer, token_servicio))
            return handleUnauthorizedResponse("El token de viewer utilizado no es válido");
        try {
            String sesion = TokenGenerator.generateSession(token_viewer);
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("insertar_sesion").withSchemaName("dbo");
            in = new MapSqlParameterSource().addValue("token_viewer", token_viewer).addValue("token_servicio", token_servicio)
                    .addValue("sesion", sesion);
            out = jdbcCall.execute(in);
            Map<String, String> data = new HashMap<>();
            data.put("sesion", sesion);
            return new RespuestaBean(Codigo.OK, "Sesion obtenida con éxito", gson.toJson(data));
        } catch (Exception e) {
            return handleErrorResponse("Error en la obtención dela sesión", e);
        }
    }

    @Override
    public RespuestaBean obtenerUrlContenido(String sesion, String eidr_contenido, String token_servicio) {
        if (!tokenValid(token_servicio))
            return handleUnauthorizedResponse("El token de servicio proporcionado no es válido");
        RespuestaBean valid_sesion = validarSesion(sesion, token_servicio);
        if (valid_sesion.getStatus() != Codigo.OK)
            return valid_sesion;
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("obtener_contenido").withSchemaName("dbo")
                    .returningResultSet("urls", BeanPropertyRowMapper.newInstance(String.class));
            in = new MapSqlParameterSource().addValue("eidr_contenido", eidr_contenido);
            out = jdbcCall.execute(in);
            if (!out.containsKey("urls"))
                throw new Exception("ERROR SQL");
            List<String> urls_obtenidas = (List<String>)out.get("urls");
            Map<String,String> urls = new HashMap<>();
            for (int i=0; i<urls_obtenidas.size(); i++){
                urls.put("url" + Integer.toString(i), urls_obtenidas.get(i));
            }
            return new RespuestaBean(Codigo.OK, "Datos obtenidos con éxito", gson.toJson(urls));
        } catch (Exception e) {
            return handleErrorResponse("Error en la obtención de la url de contenido", e);
        }
    }

    @Override
    public RespuestaBean login(int id_login, String email, String password) {
        RespuestaBean id_login_valido = validarIdLogin(id_login);
        if (id_login_valido.getStatus() != Codigo.OK)
            return id_login_valido;
        int id_servicio = Integer.parseInt(id_login_valido.getBody());
        return nuevoLogin(id_login, email, password, id_servicio, false);
    }

    @Override
    public RespuestaBean register(int id_login, String nombres, String apellidos, String email, String password) {
        try {
            RespuestaBean id_login_valido = validarIdLogin(id_login);
            if (id_login_valido.getStatus() != Codigo.OK)
                return id_login_valido;
            int id_servicio = Integer.parseInt(id_login_valido.getBody());
            int id_viewer = 0;
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withSchemaName("dbo").withProcedureName("insertar_viewer");
            in = new MapSqlParameterSource().addValue("nombres", nombres)
                                            .addValue("apellidos", apellidos)
                                            .addValue("email", email)
                                            .addValue("password", password)
                                            .addValue("id_viewer", null, Types.INTEGER);
            out = jdbcCall.execute(in);
            if (!out.containsKey("id_viewer"))
                return handleErrorResponse("Error al insertar el nuevo usuario", new Exception("ERROR SQL"));
            id_viewer = (int)out.get("id_viewer");
            if (id_viewer == 0)
                return handleErrorResponse("Error al insertar el nuevo usuario", new Exception("ERROR SQL"));
            return nuevoLogin(id_login, email, password, id_servicio, true);
        } catch (Exception e) {
            return handleErrorResponse("Error en registro", e);
        }
    }
    
    /*-------------------------------------------------------------------------------------------------------*/

    private boolean tokenValid(String token_servicio){
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("validar_token_servicio").withSchemaName("dbo");
            in = new MapSqlParameterSource().addValue("token", token_servicio).addValue("resultado", null, Types.BIT);
            out = jdbcCall.execute(in);
            return out.containsKey("resultado") && out.get("resultado") != null && (boolean)out.get("resultado");
        } catch (Exception e) {
            return false;
        }
    }

    private void usarSesion(String sesion) throws Exception{
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("usar_sesion").withSchemaName("dbo");
            in = new MapSqlParameterSource().addValue("sesion", sesion);
            jdbcCall.execute(in);
        } catch (Exception e) {
            throw e;
        }
    }

    private void obtenerDirecciones(Catalogo catalogo) throws Exception{
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("obtener_direcciones").withSchemaName("dbo")
                    .returningResultSet("direcciones", BeanPropertyRowMapper.newInstance(DireccionCatalogo.class));
            in = new MapSqlParameterSource();
            out = jdbcCall.execute(in);
            if (out.containsKey("direcciones")){
                List<DireccionCatalogo> direcciones = (List<DireccionCatalogo>)out.get("direcciones");
                for (DireccionCatalogo dir : direcciones) {
                    catalogo.addDireccion(new DireccionCatalogo(
                        dir.getEidr_contenido(),
                        dir.getNombres(),
                        dir.getApellidos()
                    ));
                }
                //catalogo.setDirecciones(direcciones);
            }
        }
        catch (Exception e){
            throw e;
        }
    }

    private void obtenerActuaciones(Catalogo catalogo) throws Exception{
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("obtener_actuaciones").withSchemaName("dbo")
                    .returningResultSet("actuaciones", BeanPropertyRowMapper.newInstance(ActuacionCatalogo.class));
            in = new MapSqlParameterSource();
            out = jdbcCall.execute(in);
            if (out.containsKey("actuaciones")){
                List<ActuacionCatalogo> actuaciones = (List<ActuacionCatalogo>)out.get("actuaciones");
                catalogo.setActuaciones(actuaciones);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void obtenerContenidos(Catalogo catalogo) throws SQLException{
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("obtener_contenidos").withSchemaName("dbo")
                    .returningResultSet("contenidos", BeanPropertyRowMapper.newInstance(ContenidoCatalogo.class));
            in = new MapSqlParameterSource();
            out = jdbcCall.execute(in);
            if (out.containsKey("contenidos")){
                List<ContenidoCatalogo> contenidos = (List<ContenidoCatalogo>)out.get("contenidos");
                catalogo.setContenidos(contenidos);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private RespuestaBean validarSesion(String sesion, String token_servicio){
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("sesion_valida").withSchemaName("dbo");
            in = new MapSqlParameterSource().addValue("token_servicio", token_servicio).addValue("sesion", sesion)
                        .addValue("resultado", null, Types.INTEGER);
            out = jdbcCall.execute(in);
            if (out.containsKey("resultado")){
                int resultado = (int)out.get("resultado");
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
            else
                return handleErrorResponse("Error al validar la sesión. Respuesta sql vacía.", new Exception("Error en ejecución de procedimiento sql"));
        } catch (Exception e) {
            return handleErrorResponse("Error al validar la sesión", e);
        }
    }
    
    private RespuestaBean handleUnauthorizedResponse(String mensaje) {
        return new RespuestaBean(Codigo.NO_AUTORIZADO, mensaje, "NO AUTORIZADO");
    }

    private RespuestaBean handleErrorResponse(String mensaje, Exception e){
        return new RespuestaBean(Codigo.ERROR, mensaje, e.getMessage());
    }

    private boolean viwerTokenValid(String viewer_token, String token_servicio){
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("validar_token_viewer").withSchemaName("dbo");
            in = new MapSqlParameterSource().addValue("token_viewer", viewer_token).addValue("token_servicio", token_servicio)
                    .addValue("resultado", null, Types.BIT);
            out = jdbcCall.execute(in);
            if (out.containsKey("resultado") && out.get("resultado") != null){
                return (boolean)out.get("resultado");
            }
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    private RespuestaBean validarIdLogin(int id_login){
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("validar_id_login").withSchemaName("dbo");
            in = new MapSqlParameterSource().addValue("id_login", id_login).addValue("valido", null, Types.BIT)
                    .addValue("id_servicio", null, Types.INTEGER);
            out = jdbcCall.execute(in);
            if (out.containsKey("valido") && out.containsKey("id_servicio")){
                boolean valido = (boolean)out.get("valido");
                int id_servicio = (int)out.get("id_servicio");
                if (!valido)
                    return handleUnauthorizedResponse("El id de login no es válido");
                return new RespuestaBean(Codigo.OK, "Id login valido", Integer.toString(id_servicio));
            }
            else
                return new RespuestaBean(Codigo.ERROR, "Error al validar el id de login", "ERROR");
        } catch (Exception e) {
            return handleErrorResponse("Error al validar el id de login", e);
        }
    }

    private RespuestaBean nuevoLogin(int id_login, String email, String password, int id_servicio, boolean viewer_nuevo){
        try {
            String token = TokenGenerator.generateToken(Integer.toString(id_login)+email+password+Integer.toString(id_servicio));
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("login_viewer").withSchemaName("dbo");
            in = new MapSqlParameterSource().addValue("id_login", id_login)
                                            .addValue("email", email)
                                            .addValue("password", password)
                                            .addValue("token", token)
                                            .addValue("id_servicio", id_servicio)
                                            .addValue("is_new", viewer_nuevo)
                                            .addValue("url_retorno", null, Types.VARCHAR);
            out = jdbcCall.execute(in);
            if (out.containsKey("url_retorno")){
                String url_retorno = (String)out.get("url_retorno");
                if (url_retorno == null || url_retorno.isEmpty())
                    return handleUnauthorizedResponse("El email y/o contraseña no son válidos");
                Map<String, String> data = new HashMap<>();
                data.put("url_retorno", url_retorno);
                return new RespuestaBean(Codigo.OK, "Login correcto", gson.toJson(data));
            }
            else
                throw new Exception("ERROR SQL");
        } catch (Exception e) {
            return handleErrorResponse("Error al generar un nuevo login", e);
        }
    }
}
