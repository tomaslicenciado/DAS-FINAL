package ar.edu.ubp.das.mss.repositories;

import ar.edu.ubp.das.mss.models.RespuestaBean;

public interface IMSSRepository {
    public RespuestaBean prueba(int id_login, String nombres, String apellidos, String email, String password);
}
