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

import ar.edu.ubp.das.positivo.beans.Codigo;
import ar.edu.ubp.das.positivo.beans.Publicidad;
import ar.edu.ubp.das.positivo.beans.RespuestaBean;

@Repository
public class PositivoRepository implements IPositivoRepository {
    @Autowired
    private JdbcTemplate jdbcTpl;

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
            System.out.println(new Gson().toJson((List<Publicidad>)out.get("publicidades")));
            return new RespuestaBean(Codigo.OK, "Publicidades obtenidas con éxito", new Gson().toJson((List<Publicidad>)out.get("publicidades")));
        } catch (Exception e) {
            return new RespuestaBean(Codigo.ERROR, "Error en la obtención de publicidades", e.getMessage());
        }
    }
    
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
