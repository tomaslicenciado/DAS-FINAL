package ar.edu.ubp.das.mss.repositories;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import ar.edu.ubp.das.mss.models.Codigo;
import ar.edu.ubp.das.mss.models.RespuestaBean;

@Repository
public class MSSRepository implements IMSSRepository{
    @Autowired
    private JdbcTemplate jdbcTpl;

    @Override
    public RespuestaBean prueba(int id_login, String nombres, String apellidos, String email, String password) {
        try {
            SoapServicesRepositories soap = new SoapServicesRepositories();
            Map<String, String> parametros = new LinkedHashMap <String, String>();
            parametros.put("email", email);
            parametros.put("nombres", nombres);
            parametros.put("apellidos", apellidos);
            parametros.put("password", password);
            parametros.put("id_login", Integer.toString(id_login));
            //Object[] parametros = {id_login, nombres, apellidos, email, password};
            RespuestaBean respuesta = soap.SoapCall("http://localhost:8084/hbo?wsdl", "register", parametros);
            return respuesta;
        } catch (Exception e) {
            return handleErrorResponse("ERRA", e);
        }
    }
    
    /*----------------------------------------------------------------------------------------------------------------------------------- */
    
    
    private RespuestaBean handleUnauthorizedResponse(String mensaje) {
        return new RespuestaBean(Codigo.NO_AUTORIZADO, mensaje, "NO AUTORIZADO");
    }

    private RespuestaBean handleErrorResponse(String mensaje, Exception e){
        e.printStackTrace();
        return new RespuestaBean(Codigo.ERROR, mensaje, e.toString());
    }
}
