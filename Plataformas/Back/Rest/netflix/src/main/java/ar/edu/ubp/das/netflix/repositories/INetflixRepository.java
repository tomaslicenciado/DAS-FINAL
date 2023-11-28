package ar.edu.ubp.das.netflix.repositories;

import ar.edu.ubp.das.netflix.beans.RespuestaBean;

public interface INetflixRepository {
    public RespuestaBean obtener_login_url(String url_retorno, String token_servicio);
    public RespuestaBean obtener_token_viewer(String transaction_id, String token_servicio);
    public RespuestaBean es_usuario_nuevo(String transaction_id, String token_servicio);
    public RespuestaBean obtener_catalogo(String sesion, String token_servicio);
    public RespuestaBean obtener_sesion(String token_viewer, String token_servicio);
    public RespuestaBean obtener_url_contenido(String sesion, String eidr_contenido, String token_servicio);
    public RespuestaBean login(int id_login, String email, String password);
    public RespuestaBean register(int id_login, String nombres, String apellidos, String email, String password);
}
