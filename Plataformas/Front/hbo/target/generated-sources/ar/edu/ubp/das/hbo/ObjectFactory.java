
package ar.edu.ubp.das.hbo;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ar.edu.ubp.das.hbo package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EsUsuarioNuevo_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "esUsuarioNuevo");
    private final static QName _EsUsuarioNuevoResponse_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "esUsuarioNuevoResponse");
    private final static QName _Login_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "login");
    private final static QName _LoginResponse_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "loginResponse");
    private final static QName _ObtenerCatalogo_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "obtenerCatalogo");
    private final static QName _ObtenerCatalogoResponse_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "obtenerCatalogoResponse");
    private final static QName _ObtenerLoginUrl_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "obtenerLoginUrl");
    private final static QName _ObtenerLoginUrlResponse_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "obtenerLoginUrlResponse");
    private final static QName _ObtenerSesion_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "obtenerSesion");
    private final static QName _ObtenerSesionResponse_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "obtenerSesionResponse");
    private final static QName _ObtenerTokenViewer_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "obtenerTokenViewer");
    private final static QName _ObtenerTokenViewerResponse_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "obtenerTokenViewerResponse");
    private final static QName _ObtenerUrlContenido_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "obtenerUrlContenido");
    private final static QName _ObtenerUrlContenidoResponse_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "obtenerUrlContenidoResponse");
    private final static QName _Register_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "register");
    private final static QName _RegisterResponse_QNAME = new QName("http://platforms.hbo.das.ubp.edu.ar/", "registerResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ar.edu.ubp.das.hbo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EsUsuarioNuevo }
     * 
     */
    public EsUsuarioNuevo createEsUsuarioNuevo() {
        return new EsUsuarioNuevo();
    }

    /**
     * Create an instance of {@link EsUsuarioNuevoResponse }
     * 
     */
    public EsUsuarioNuevoResponse createEsUsuarioNuevoResponse() {
        return new EsUsuarioNuevoResponse();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link ObtenerCatalogo }
     * 
     */
    public ObtenerCatalogo createObtenerCatalogo() {
        return new ObtenerCatalogo();
    }

    /**
     * Create an instance of {@link ObtenerCatalogoResponse }
     * 
     */
    public ObtenerCatalogoResponse createObtenerCatalogoResponse() {
        return new ObtenerCatalogoResponse();
    }

    /**
     * Create an instance of {@link ObtenerLoginUrl }
     * 
     */
    public ObtenerLoginUrl createObtenerLoginUrl() {
        return new ObtenerLoginUrl();
    }

    /**
     * Create an instance of {@link ObtenerLoginUrlResponse }
     * 
     */
    public ObtenerLoginUrlResponse createObtenerLoginUrlResponse() {
        return new ObtenerLoginUrlResponse();
    }

    /**
     * Create an instance of {@link ObtenerSesion }
     * 
     */
    public ObtenerSesion createObtenerSesion() {
        return new ObtenerSesion();
    }

    /**
     * Create an instance of {@link ObtenerSesionResponse }
     * 
     */
    public ObtenerSesionResponse createObtenerSesionResponse() {
        return new ObtenerSesionResponse();
    }

    /**
     * Create an instance of {@link ObtenerTokenViewer }
     * 
     */
    public ObtenerTokenViewer createObtenerTokenViewer() {
        return new ObtenerTokenViewer();
    }

    /**
     * Create an instance of {@link ObtenerTokenViewerResponse }
     * 
     */
    public ObtenerTokenViewerResponse createObtenerTokenViewerResponse() {
        return new ObtenerTokenViewerResponse();
    }

    /**
     * Create an instance of {@link ObtenerUrlContenido }
     * 
     */
    public ObtenerUrlContenido createObtenerUrlContenido() {
        return new ObtenerUrlContenido();
    }

    /**
     * Create an instance of {@link ObtenerUrlContenidoResponse }
     * 
     */
    public ObtenerUrlContenidoResponse createObtenerUrlContenidoResponse() {
        return new ObtenerUrlContenidoResponse();
    }

    /**
     * Create an instance of {@link Register }
     * 
     */
    public Register createRegister() {
        return new Register();
    }

    /**
     * Create an instance of {@link RegisterResponse }
     * 
     */
    public RegisterResponse createRegisterResponse() {
        return new RegisterResponse();
    }

    /**
     * Create an instance of {@link RespuestaBean }
     * 
     */
    public RespuestaBean createRespuestaBean() {
        return new RespuestaBean();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsUsuarioNuevo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EsUsuarioNuevo }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "esUsuarioNuevo")
    public JAXBElement<EsUsuarioNuevo> createEsUsuarioNuevo(EsUsuarioNuevo value) {
        return new JAXBElement<EsUsuarioNuevo>(_EsUsuarioNuevo_QNAME, EsUsuarioNuevo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsUsuarioNuevoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EsUsuarioNuevoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "esUsuarioNuevoResponse")
    public JAXBElement<EsUsuarioNuevoResponse> createEsUsuarioNuevoResponse(EsUsuarioNuevoResponse value) {
        return new JAXBElement<EsUsuarioNuevoResponse>(_EsUsuarioNuevoResponse_QNAME, EsUsuarioNuevoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Login }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerCatalogo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerCatalogo }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "obtenerCatalogo")
    public JAXBElement<ObtenerCatalogo> createObtenerCatalogo(ObtenerCatalogo value) {
        return new JAXBElement<ObtenerCatalogo>(_ObtenerCatalogo_QNAME, ObtenerCatalogo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerCatalogoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerCatalogoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "obtenerCatalogoResponse")
    public JAXBElement<ObtenerCatalogoResponse> createObtenerCatalogoResponse(ObtenerCatalogoResponse value) {
        return new JAXBElement<ObtenerCatalogoResponse>(_ObtenerCatalogoResponse_QNAME, ObtenerCatalogoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerLoginUrl }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerLoginUrl }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "obtenerLoginUrl")
    public JAXBElement<ObtenerLoginUrl> createObtenerLoginUrl(ObtenerLoginUrl value) {
        return new JAXBElement<ObtenerLoginUrl>(_ObtenerLoginUrl_QNAME, ObtenerLoginUrl.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerLoginUrlResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerLoginUrlResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "obtenerLoginUrlResponse")
    public JAXBElement<ObtenerLoginUrlResponse> createObtenerLoginUrlResponse(ObtenerLoginUrlResponse value) {
        return new JAXBElement<ObtenerLoginUrlResponse>(_ObtenerLoginUrlResponse_QNAME, ObtenerLoginUrlResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerSesion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerSesion }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "obtenerSesion")
    public JAXBElement<ObtenerSesion> createObtenerSesion(ObtenerSesion value) {
        return new JAXBElement<ObtenerSesion>(_ObtenerSesion_QNAME, ObtenerSesion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerSesionResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerSesionResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "obtenerSesionResponse")
    public JAXBElement<ObtenerSesionResponse> createObtenerSesionResponse(ObtenerSesionResponse value) {
        return new JAXBElement<ObtenerSesionResponse>(_ObtenerSesionResponse_QNAME, ObtenerSesionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerTokenViewer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerTokenViewer }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "obtenerTokenViewer")
    public JAXBElement<ObtenerTokenViewer> createObtenerTokenViewer(ObtenerTokenViewer value) {
        return new JAXBElement<ObtenerTokenViewer>(_ObtenerTokenViewer_QNAME, ObtenerTokenViewer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerTokenViewerResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerTokenViewerResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "obtenerTokenViewerResponse")
    public JAXBElement<ObtenerTokenViewerResponse> createObtenerTokenViewerResponse(ObtenerTokenViewerResponse value) {
        return new JAXBElement<ObtenerTokenViewerResponse>(_ObtenerTokenViewerResponse_QNAME, ObtenerTokenViewerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerUrlContenido }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerUrlContenido }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "obtenerUrlContenido")
    public JAXBElement<ObtenerUrlContenido> createObtenerUrlContenido(ObtenerUrlContenido value) {
        return new JAXBElement<ObtenerUrlContenido>(_ObtenerUrlContenido_QNAME, ObtenerUrlContenido.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerUrlContenidoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerUrlContenidoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "obtenerUrlContenidoResponse")
    public JAXBElement<ObtenerUrlContenidoResponse> createObtenerUrlContenidoResponse(ObtenerUrlContenidoResponse value) {
        return new JAXBElement<ObtenerUrlContenidoResponse>(_ObtenerUrlContenidoResponse_QNAME, ObtenerUrlContenidoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Register }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Register }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "register")
    public JAXBElement<Register> createRegister(Register value) {
        return new JAXBElement<Register>(_Register_QNAME, Register.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RegisterResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://platforms.hbo.das.ubp.edu.ar/", name = "registerResponse")
    public JAXBElement<RegisterResponse> createRegisterResponse(RegisterResponse value) {
        return new JAXBElement<RegisterResponse>(_RegisterResponse_QNAME, RegisterResponse.class, null, value);
    }

}
