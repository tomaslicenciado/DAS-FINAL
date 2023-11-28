package ar.edu.ubp.das.netflix.repositories;

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.google.gson.Gson;

import ar.edu.ubp.das.netflix.beans.ActuacionCatalogo;
import ar.edu.ubp.das.netflix.beans.Catalogo;
import ar.edu.ubp.das.netflix.beans.Codigo;
import ar.edu.ubp.das.netflix.beans.ContenidoCatalogo;
import ar.edu.ubp.das.netflix.beans.DireccionCatalogo;
import ar.edu.ubp.das.netflix.beans.RespuestaBean;
import ar.edu.ubp.das.netflix.utils.TokenGenerator;

public class NetflixRepository implements INetflixRepository {
    @Autowired
    private JdbcTemplate jdbcTpl;

    private SqlParameterSource in;
    private SimpleJdbcCall jdbcCall;
    private Map<String, Object> out;

    @Override
    public RespuestaBean obtener_login_url(String url_retorno, String token_servicio) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtener_login_url'");
    }

    @Override
    public RespuestaBean obtener_token_viewer(String transaction_id, String token_servicio) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtener_token_viewer'");
    }

    @Override
    public RespuestaBean es_usuario_nuevo(String transaction_id, String token_servicio) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'es_usuario_nuevo'");
    }

    @Override
    public RespuestaBean obtener_catalogo(String sesion, String token_servicio) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtener_catalogo'");
    }

    @Override
    public RespuestaBean obtener_sesion(String token_viewer, String token_servicio) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtener_sesion'");
    }

    @Override
    public RespuestaBean obtener_url_contenido(String sesion, String eidr_contenido, String token_servicio) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtener_url_contenido'");
    }

    @Override
    public RespuestaBean login(int id_login, String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public RespuestaBean register(int id_login, String nombres, String apellidos, String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }
    
    /*-------------------------------------------------------------------------------------------------------*/

    private boolean tokenValid(String token_servicio){
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("validar_token_servicio").withSchemaName("dbo");
            in = new MapSqlParameterSource().addValue("@token", token_servicio).addValue("@resultado", null, Types.BIT);
            out = jdbcCall.execute(in);
            return out.containsKey("resultado") && out.get("@resultado") != null && (boolean)out.get("@resultado");
        } catch (Exception e) {
            return false;
        }
    }

    private void usarSesion(String sesion) throws Exception{
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("usar_sesion").withSchemaName("dbo");
            in = new MapSqlParameterSource().addValue("@sesion", sesion);
            jdbcCall.execute(in);
        } catch (Exception e) {
            throw e;
        }
    }

    private void ObtenerDirecciones(Catalogo catalogo) throws Exception{
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("obtener_direcciones").withSchemaName("dbo")
                    .returningResultSet("direcciones", BeanPropertyRowMapper.newInstance(DireccionCatalogo.class));
            in = new MapSqlParameterSource();
            out = jdbcCall.execute(in);
            if (out.containsKey("direcciones")){
                List<DireccionCatalogo> direcciones = (List<DireccionCatalogo>)out.get("direcciones");
                catalogo.setDirecciones(direcciones);
            }
        }
        catch (Exception e){
            throw e;
        }
    }

    private void ObtenerActuaciones(Catalogo catalogo) throws Exception{
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("obtener_actuaciones").withSchemaName("dbo")
                    .returningResultSet("actuaciones", BeanPropertyRowMapper.newInstance(ActuacionCatalogo.class));
            in = new MapSqlParameterSource();
            out = jdbcCall.execute(in);
            if (out.containsKey("actuaciones")){
                List<ActuacionCatalogo> actuaciones = (List<ActuacionCatalogo>)out.get("direcciones");
                catalogo.setActuaciones(actuaciones);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void ObtenerContenidos(Catalogo catalogo) throws SQLException{
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("obtener_contenidos").withSchemaName("dbo")
                    .returningResultSet("contenidos", BeanPropertyRowMapper.newInstance(ContenidoCatalogo.class));
            in = new MapSqlParameterSource();
            out = jdbcCall.execute(in);
            if (out.containsKey("contenidos")){
                List<ContenidoCatalogo> contenidos = (List<ContenidoCatalogo>)out.get("direcciones");
                catalogo.setContenidos(contenidos);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private RespuestaBean validar_sesion(String sesion, String token_servicio){
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("sesion_valida").withSchemaName("dbo");
            in = new MapSqlParameterSource().addValue("@token_servicio", token_servicio).addValue("@sesion", sesion)
                        .addValue("@resultado", null, Types.BIT);
            out = jdbcCall.execute(in);
            if (out.containsKey("@resultado")){
                int resultado = (int)out.get("@resultado");
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
        return new RespuestaBean(Codigo.ERROR, mensaje, e.toString());
    }

    private boolean viwer_token_valid(String viewer_token, String token_servicio){
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

    private RespuestaBean validar_id_login(int id_login){
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

    private RespuestaBean nuevo_login(int id_login, String email, String password, int id_servicio, boolean viewer_nuevo){
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
