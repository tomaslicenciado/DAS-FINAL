
package ar.edu.ubp.das.hbo;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 3.0.0
 * Generated source version: 3.0
 * 
 */
@WebService(name = "HBOWS", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface HBOWS {


    /**
     * 
     * @param tokenServicio
     * @param transactionId
     * @return
     *     returns ar.edu.ubp.das.hbo.RespuestaBean
     */
    @WebMethod
    @WebResult(name = "respuesta", targetNamespace = "")
    @RequestWrapper(localName = "obtenerTokenViewer", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.ObtenerTokenViewer")
    @ResponseWrapper(localName = "obtenerTokenViewerResponse", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.ObtenerTokenViewerResponse")
    public RespuestaBean obtenerTokenViewer(
        @WebParam(name = "transaction_id", targetNamespace = "")
        String transactionId,
        @WebParam(name = "token_servicio", targetNamespace = "")
        String tokenServicio);

    /**
     * 
     * @param tokenServicio
     * @param sesion
     * @return
     *     returns ar.edu.ubp.das.hbo.RespuestaBean
     */
    @WebMethod
    @WebResult(name = "respuesta", targetNamespace = "")
    @RequestWrapper(localName = "obtenerCatalogo", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.ObtenerCatalogo")
    @ResponseWrapper(localName = "obtenerCatalogoResponse", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.ObtenerCatalogoResponse")
    public RespuestaBean obtenerCatalogo(
        @WebParam(name = "sesion", targetNamespace = "")
        String sesion,
        @WebParam(name = "token_servicio", targetNamespace = "")
        String tokenServicio);

    /**
     * 
     * @param tokenServicio
     * @param transactionId
     * @return
     *     returns ar.edu.ubp.das.hbo.RespuestaBean
     */
    @WebMethod
    @WebResult(name = "respuesta", targetNamespace = "")
    @RequestWrapper(localName = "esUsuarioNuevo", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.EsUsuarioNuevo")
    @ResponseWrapper(localName = "esUsuarioNuevoResponse", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.EsUsuarioNuevoResponse")
    public RespuestaBean esUsuarioNuevo(
        @WebParam(name = "transaction_id", targetNamespace = "")
        String transactionId,
        @WebParam(name = "token_servicio", targetNamespace = "")
        String tokenServicio);

    /**
     * 
     * @param tokenServicio
     * @param tokenViewer
     * @return
     *     returns ar.edu.ubp.das.hbo.RespuestaBean
     */
    @WebMethod
    @WebResult(name = "respuesta", targetNamespace = "")
    @RequestWrapper(localName = "obtenerSesion", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.ObtenerSesion")
    @ResponseWrapper(localName = "obtenerSesionResponse", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.ObtenerSesionResponse")
    public RespuestaBean obtenerSesion(
        @WebParam(name = "token_viewer", targetNamespace = "")
        String tokenViewer,
        @WebParam(name = "token_servicio", targetNamespace = "")
        String tokenServicio);

    /**
     * 
     * @param eidrContenido
     * @param tokenServicio
     * @param sesion
     * @return
     *     returns ar.edu.ubp.das.hbo.RespuestaBean
     */
    @WebMethod
    @WebResult(name = "respuesta", targetNamespace = "")
    @RequestWrapper(localName = "obtenerUrlContenido", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.ObtenerUrlContenido")
    @ResponseWrapper(localName = "obtenerUrlContenidoResponse", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.ObtenerUrlContenidoResponse")
    public RespuestaBean obtenerUrlContenido(
        @WebParam(name = "sesion", targetNamespace = "")
        String sesion,
        @WebParam(name = "eidr_contenido", targetNamespace = "")
        String eidrContenido,
        @WebParam(name = "token_servicio", targetNamespace = "")
        String tokenServicio);

    /**
     * 
     * @param password
     * @param idLogin
     * @param email
     * @return
     *     returns ar.edu.ubp.das.hbo.RespuestaBean
     */
    @WebMethod
    @WebResult(name = "validacion", targetNamespace = "")
    @RequestWrapper(localName = "login", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.Login")
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.LoginResponse")
    public RespuestaBean login(
        @WebParam(name = "id_login", targetNamespace = "")
        int idLogin,
        @WebParam(name = "email", targetNamespace = "")
        String email,
        @WebParam(name = "password", targetNamespace = "")
        String password);

    /**
     * 
     * @param tokenServicio
     * @param urlRetorno
     * @return
     *     returns ar.edu.ubp.das.hbo.RespuestaBean
     */
    @WebMethod
    @WebResult(name = "respuesta", targetNamespace = "")
    @RequestWrapper(localName = "obtenerLoginUrl", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.ObtenerLoginUrl")
    @ResponseWrapper(localName = "obtenerLoginUrlResponse", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.ObtenerLoginUrlResponse")
    public RespuestaBean obtenerLoginUrl(
        @WebParam(name = "url_retorno", targetNamespace = "")
        String urlRetorno,
        @WebParam(name = "token_servicio", targetNamespace = "")
        String tokenServicio);

    /**
     * 
     * @param apellidos
     * @param password
     * @param idLogin
     * @param email
     * @param nombres
     * @return
     *     returns ar.edu.ubp.das.hbo.RespuestaBean
     */
    @WebMethod
    @WebResult(name = "validacion", targetNamespace = "")
    @RequestWrapper(localName = "register", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.Register")
    @ResponseWrapper(localName = "registerResponse", targetNamespace = "http://platforms.hbo.das.ubp.edu.ar/", className = "ar.edu.ubp.das.hbo.RegisterResponse")
    public RespuestaBean register(
        @WebParam(name = "id_login", targetNamespace = "")
        int idLogin,
        @WebParam(name = "nombres", targetNamespace = "")
        String nombres,
        @WebParam(name = "apellidos", targetNamespace = "")
        String apellidos,
        @WebParam(name = "email", targetNamespace = "")
        String email,
        @WebParam(name = "password", targetNamespace = "")
        String password);

}
