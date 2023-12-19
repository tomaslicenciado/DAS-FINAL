package ar.edu.ubp.das.mss.repositories;

import java.util.Date;
import java.util.List;

import ar.edu.ubp.das.mss.models.RespuestaBean;

public interface IMSSRepository {
    /*Iniciados por el suscriptor */
    public RespuestaBean iniciarSesion(String email, String password);
    public RespuestaBean registrarSuscriptor(String email, String password, String nombres, String apellidos, List<Integer> id_preferencias_generos);
    public RespuestaBean iniciarFederacionPlataforma(int id_plataforma, String token_suscriptor, String url_retorno);
    public RespuestaBean finalizarFederacionPlataforma(String token_suscriptor, int id_plataforma);
    public RespuestaBean desuscribirPlataforma(int id_plataforma, String token_suscriptor);
    public RespuestaBean obtenerContenido(String token_suscriptor, int id_plataforma, String eidr_contenido);
    /*Iniciados por el frontend */
    public RespuestaBean obtenerListadoGenerosContenido(String token_usuario);
    public RespuestaBean obtenerListadoPlataformas(String token_usuario);
    public RespuestaBean obtenerCatalogo(String token_suscriptor);
    public RespuestaBean obtenerContenidosMasVistos(String token_suscriptor);
    public RespuestaBean obtenerPublicidades(String token_suscriptor);
    public RespuestaBean registrarVisualizacion(String token_suscriptor, int id_plataforma, String eidr_contenido);
    public RespuestaBean registrarAccesoPublicidad(String token_suscriptor, int id_publicidad);
    public RespuestaBean obtenerListadoPublicistas(String token_usuario);
    public RespuestaBean obtenerListadoTiposServicio(String token_usuario);
    public RespuestaBean obtenerListadoBanners(String token_usuario);
    /*Iniciados por admin */
    public RespuestaBean registrarNuevaPlataforma(String token_usuario, String nombre, String url_conexion, String url_icono, 
                                                    String email, int id_tipo_servicio, String token_servicio, float tarifa_nuevos_viewers, float tarifa_viewers_activos);
    public RespuestaBean actualizarPlataforma(String token_usuario, int id_plataforma, String nombre, String url_conexion, String url_icono, 
                                                String email, int id_tipo_servicio, String token_servicio, float tarifa_nuevos_viewers, float tarifa_viewers_activos);
    public RespuestaBean eliminarPlataforma(String token_usuario, int id_plataforma);
    public RespuestaBean modificarBanner(String token_usuario, int banner_code, float tarifa_base, float tarifa_exclusividad);
    public RespuestaBean agregarPublicista(String token_usuario, String nombre, String razon_social, String email, int telefono, 
                                            String nombre_contacto, String url_conexion, String token_servicio, int id_tipo_servicio);
    public RespuestaBean modificarPublicista(String token_usuario, int id_publicista, String nombre, String razon_social, String email, int telefono, String nombre_contacto,
                                            int id_tipo_servicio, String url_conexion, String token_servicio);
    public RespuestaBean eliminarPublicista(String token_usuario, int id_publicista);
    public RespuestaBean agregarPublicidad(String token_usuario, int id_publicista, int banner_code, boolean exclusiva, Date fecha_inicio, Date fecha_fin);
    public RespuestaBean modificarPublicidad(String token_usuario, int id_publicidad, boolean exclusiva, Date fecha_inicio, Date fecha_fin);
    public RespuestaBean eliminarPublicidad(String token_usuario, int id_publicidad);
    public RespuestaBean obtenerFeesAPagar(String token_usuario);
    /*Usuarios en general */
    public RespuestaBean modificarDatosPersonales(String token_usuario, String nombres, String apellidos, String password);
    /*Para ser utilizadas en tareas programadas */
    public void finalizarFederacionesPendientes() throws Exception;
    public void enviarFacturas() throws Exception;
    public void enviarEstadisticas() throws Exception;
    public void actualizarCatalogo() throws Exception;
    public void actualizarPublicidades() throws Exception;
}
