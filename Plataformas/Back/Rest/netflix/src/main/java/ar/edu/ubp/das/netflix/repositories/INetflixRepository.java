package ar.edu.ubp.das.netflix.repositories;

import ar.edu.ubp.das.netflix.beans.RespuestaBean;

public interface INetflixRepository {
    public RespuestaBean obtenerLoginUrl(String url_retorno, String token_servicio);
    public RespuestaBean obtenerTokenViewer(String transaction_id, String token_servicio);
    public RespuestaBean esUsuarioNuevo(String transaction_id, String token_servicio);
    public RespuestaBean obtenerCatalogo(String sesion, String token_servicio);
    public RespuestaBean obtenerSesion(String token_viewer, String token_servicio);
    public RespuestaBean obtenerUrlContenido(String sesion, String eidr_contenido, String token_servicio);
    public RespuestaBean login(int id_login, String email, String password);
    public RespuestaBean register(int id_login, String nombres, String apellidos, String email, String password);
}
