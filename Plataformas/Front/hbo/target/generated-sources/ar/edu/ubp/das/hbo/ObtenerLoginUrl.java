
package ar.edu.ubp.das.hbo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerLoginUrl complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerLoginUrl"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="url_retorno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="token_servicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerLoginUrl", propOrder = {
    "urlRetorno",
    "tokenServicio"
})
public class ObtenerLoginUrl {

    @XmlElement(name = "url_retorno")
    protected String urlRetorno;
    @XmlElement(name = "token_servicio")
    protected String tokenServicio;

    /**
     * Obtiene el valor de la propiedad urlRetorno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlRetorno() {
        return urlRetorno;
    }

    /**
     * Define el valor de la propiedad urlRetorno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlRetorno(String value) {
        this.urlRetorno = value;
    }

    /**
     * Obtiene el valor de la propiedad tokenServicio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenServicio() {
        return tokenServicio;
    }

    /**
     * Define el valor de la propiedad tokenServicio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenServicio(String value) {
        this.tokenServicio = value;
    }

}
