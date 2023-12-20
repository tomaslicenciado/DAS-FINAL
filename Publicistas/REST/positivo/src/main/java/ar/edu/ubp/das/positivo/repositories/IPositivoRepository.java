package ar.edu.ubp.das.positivo.repositories;

import ar.edu.ubp.das.positivo.beans.RespuestaBean;

public interface IPositivoRepository {
    public RespuestaBean obtenerPublicidades(String token_servicio);
    public RespuestaBean insertarEstadisticas(String token_servicio, String estadisticas_accesos_json);
}
