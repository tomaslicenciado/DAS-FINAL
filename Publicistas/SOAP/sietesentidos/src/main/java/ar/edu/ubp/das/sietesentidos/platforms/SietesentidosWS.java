package ar.edu.ubp.das.sietesentidos.platforms;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ar.edu.ubp.das.sietesentidos.beans.Codigo;
import ar.edu.ubp.das.sietesentidos.beans.Publicidad;
import ar.edu.ubp.das.sietesentidos.beans.RegistroEstadisticoAcceso;
import ar.edu.ubp.das.sietesentidos.beans.RespuestaBean;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@WebService
@XmlSeeAlso({Publicidad.class, RespuestaBean.class})
public class SietesentidosWS {
    private String driver_sql;
    private String sql_conection_string;
    private String sql_user;
    private String sql_pass;
    private Gson gson;

    public SietesentidosWS(){
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
            driver_sql = properties.getProperty("driver_sql");;
            sql_conection_string = properties.getProperty("sql_conection_string");;
            sql_user = properties.getProperty("sql_user");;
            sql_pass = properties.getProperty("sql_pass");;
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd-MM-yyyy");
        gson = gsonBuilder.create();
    }

    @WebMethod()
    @WebResult(name = "publicidades")
    public RespuestaBean obtenerPublicidades(@WebParam(name = "token_servicio") String token_servicio){
        RespuestaBean respuesta = new RespuestaBean();
        if (!tokenValid(token_servicio)){
            respuesta.setStatus(Codigo.NO_AUTORIZADO);
            respuesta.setMensaje("El token proporcionado no es válido");
            respuesta.setBody("Error");
            return respuesta;
        }
        try{
            List<Publicidad> publicidades = new LinkedList<Publicidad>();
        
            Connection conn;
            CallableStatement stmt;
            ResultSet rs;
            Class.forName(driver_sql);
            conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
            conn.setAutoCommit(true);
            
            stmt = conn.prepareCall("{CALL dbo.obtener_publicidades(?)}");
            stmt.setString("token_servicio", token_servicio);

            rs = stmt.executeQuery();
            while (rs.next()){
                Publicidad pub = new Publicidad();
                pub.setBanner_code(rs.getInt("banner_code"));
                pub.setUrl_imagen(rs.getString("url_imagen"));
                pub.setUrl_contenido(rs.getString("url_contenido"));
                pub.setCodigo_unico_id(rs.getInt("codigo_unico_id"));
                publicidades.add(pub);
            }

            stmt.close();
            conn.close();
            rs.close();

            respuesta.setStatus(Codigo.OK);
            respuesta.setBody(gson.toJson(publicidades));
            respuesta.setMensaje("Publicidades obtenidas");
            return respuesta;
        }
        catch (Exception ex){
            respuesta.setStatus(Codigo.ERROR);
            respuesta.setBody(ex.getMessage());
            respuesta.setMensaje("Error en la obtención de publicidades");
            return respuesta;
        }
    }

    @WebMethod()
    @WebResult(name = "respuesta")
    public RespuestaBean insertarEstadisticas(@WebParam(name = "token_servicio") String token_servicio, 
                                @WebParam(name = "estadisticas_accesos_json") String estadisticas_accesos_json){
        try {
            if (!tokenValid(token_servicio))
                return new RespuestaBean(Codigo.NO_AUTORIZADO, "El token de servicio proporcionado no es válido", "ERROR");
            Connection conn;
            CallableStatement stmt;
            ResultSet rs;
            Class.forName(driver_sql);
            conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
            conn.setAutoCommit(true);
            
            stmt = conn.prepareCall("{CALL dbo.insertar_estadisticas_externas(?)}");
            stmt.setString("json", estadisticas_accesos_json);
            
            stmt.execute();
            return new RespuestaBean(Codigo.OK, "Estadísticas registradas con éxito", null);
        } catch (Exception e) {
            return new RespuestaBean(Codigo.ERROR, "Error al insertar estadísticas",e.getMessage());
        }
    }

    private boolean tokenValid(String token_servicio){
        try {
            Connection conn;
            CallableStatement stmt;

            Class.forName(driver_sql);
            conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
            conn.setAutoCommit(true);

            stmt = conn.prepareCall("{CALL dbo.validar_token(?, ?)}");
            stmt.setString(1, token_servicio);
            stmt.registerOutParameter(2, java.sql.Types.BIT); 

            stmt.execute();

            boolean resultado = stmt.getBoolean(2);

            stmt.close();
            conn.close();

            return resultado;
        } catch (Exception e) {
            return false;
        }
    }
}
