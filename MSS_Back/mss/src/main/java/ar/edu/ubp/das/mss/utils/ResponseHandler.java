package ar.edu.ubp.das.mss.utils;

import ar.edu.ubp.das.mss.models.Codigo;
import ar.edu.ubp.das.mss.models.RespuestaBean;

public class ResponseHandler {
    public static RespuestaBean handleUnauthorizedResponse(String mensaje) {
        return new RespuestaBean(Codigo.NO_AUTORIZADO, mensaje, "NO AUTORIZADO");
    }

    public static RespuestaBean handleErrorResponse(String mensaje, Exception e){
        e.printStackTrace();
        return new RespuestaBean(Codigo.ERROR, mensaje, e.getMessage());
    }

    public static RespuestaBean handleSQLErrorResponse(String mensaje, String operacion){
        return new RespuestaBean(Codigo.ERROR, mensaje, "ERROR SQL en " + operacion);
    }
}
