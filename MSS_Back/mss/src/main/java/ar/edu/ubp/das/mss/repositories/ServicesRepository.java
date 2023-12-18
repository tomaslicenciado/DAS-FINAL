package ar.edu.ubp.das.mss.repositories;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.mss.models.RespuestaBean;
import ar.edu.ubp.das.mss.models.ServicioBean;
import ar.edu.ubp.das.mss.utils.ResponseHandler;

@Repository
public class ServicesRepository {
    @Autowired
    private JdbcTemplate jdbcTpl;

    public RespuestaBean consultarPlataforma(int id_servicio, String operacion, Map<String, String> parametros){
        return consultarServicio(id_servicio, true, operacion, parametros);
    }

    public RespuestaBean consultarPublicista(int id_servicio, String operacion, Map<String, String> parametros){
        return consultarServicio(id_servicio, false, operacion, parametros);
    }

    private RespuestaBean consultarServicio (int id_servicio, boolean esPlataforma, String operacion, Map<String, String> parametros){
        try {
            ServicioBean servicio = obtenerServicio(id_servicio, esPlataforma);
            //Agrego el token de servicio obtenido
            if (parametros == null)
                parametros = new LinkedHashMap<>();
            parametros.put("token_servicio", servicio.getToken_servicio());
            //SOAP
            if (servicio.getTipo().toLowerCase().contains("soap"))
                return SoapServicesRepository.SoapCall(servicio.getUrl_conexion()+"?wsdl", operacion, parametros);
            else
                return RestServicesRepository.RestCall(servicio.getUrl_conexion(), operacion, parametros, MediaType.APPLICATION_JSON);
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al consultar la operación " + operacion, e);
        }
    }

    private ServicioBean obtenerServicio(int id_servicio, boolean esPlataforma) throws Exception {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl).withSchemaName("dbo").withProcedureName("obtener_datos_servicio")
                                        .returningResultSet("datos", BeanPropertyRowMapper.newInstance(ServicioBean.class));
        SqlParameterSource in = new MapSqlParameterSource().addValue("id_servicio", id_servicio).addValue("es_plataforma", esPlataforma);
        Map<String, Object> out = jdbcCall.execute(in);
        if (!out.containsKey("datos"))
            throw new Exception("Error sql al obtener los datos del servicio");
        List<ServicioBean> servicios = (List<ServicioBean>)out.get("datos");
        ServicioBean servicio = servicios.get(0);
        if (servicio.getToken_servicio() == null || servicio.getToken_servicio().isEmpty())
            throw new Exception("Error sql. El servicio obtenido no tiene token asociado");
        if (servicio.getUrl_conexion() == null || servicio.getUrl_conexion().isEmpty())
            throw new Exception("Error sql. El servicio obtenido no tiene url de conexión de servicio");
        return servicio;
    }
}
