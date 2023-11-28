package ar.edu.ubp.das.positivo.repositories;

import ar.edu.ubp.das.positivo.beans.RespuestaBean;

public interface IPositivoRepository {
    public RespuestaBean obtenerPublicidades(String token_servicio);
}
