package ar.edu.ubp.das.positivo.repositories;

import java.sql.Types;
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
import com.google.gson.reflect.TypeToken;

import ar.edu.ubp.das.positivo.beans.Codigo;
import ar.edu.ubp.das.positivo.beans.Publicidad;
import ar.edu.ubp.das.positivo.beans.RegistroEstadisticoAcceso;
import ar.edu.ubp.das.positivo.beans.RespuestaBean;

@Repository
public class PositivoRepository implements IPositivoRepository {
    @Autowired
    private JdbcTemplate jdbcTpl;

    private Gson gson;

    PositivoRepository(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd-MM-yyyy");
        gson = gsonBuilder.create();
    }

    @Override
    public RespuestaBean obtenerPublicidades(String token_servicio) {
        if (!tokenValid(token_servicio)){
            return new RespuestaBean(Codigo.NO_AUTORIZADO,"El token proporcionado no es válido", "ERROR");
        }
        try {
            SqlParameterSource in = new MapSqlParameterSource().addValue("token_servicio", token_servicio);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("obtener_publicidades").withSchemaName("dbo")
                .returningResultSet("publicidades", BeanPropertyRowMapper.newInstance(Publicidad.class));
            Map<String, Object> out = jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Publicidades obtenidas con éxito", gson.toJson((List<Publicidad>)out.get("publicidades")));
        } catch (Exception e) {
            return new RespuestaBean(Codigo.ERROR, "Error en la obtención de publicidades", e.getMessage());
        }
    }

    @Override
    public RespuestaBean insertarEstadisticas(String token_servicio, String estadisticas_accesos_json){
        try {
            System.out.println("Me llamaron");
            if (!tokenValid(token_servicio))
                return new RespuestaBean(Codigo.NO_AUTORIZADO, "El token de servicio proporcionado no es válido", "ERROR");
            System.out.println(estadisticas_accesos_json);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl).withSchemaName("dbo").withProcedureName("insertar_estadisticas_externas");
            SqlParameterSource in = new MapSqlParameterSource().addValue("json", estadisticas_accesos_json);
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Estadísticas registradas con éxito", null);
        } catch (Exception e) {
            return new RespuestaBean(Codigo.ERROR, "Error al insertar estadísticas",e.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    private boolean tokenValid(String token_servicio){
        try {
            SqlParameterSource in = new MapSqlParameterSource().addValue("token", token_servicio).addValue("resultado", null, Types.BIT);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("validar_token").withSchemaName("dbo");
            Map<String, Object> out = jdbcCall.execute(in);
            return out.containsKey("resultado") && out.get("resultado") != null && (boolean)out.get("resultado");
        } catch (Exception e) {
            return false;
        }
    }
}
