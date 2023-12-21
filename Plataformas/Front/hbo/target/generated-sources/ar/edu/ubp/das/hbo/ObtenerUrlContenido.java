
package ar.edu.ubp.das.hbo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerUrlContenido complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerUrlContenido"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="sesion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="eidr_contenido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "obtenerUrlContenido", propOrder = {
    "sesion",
    "eidrContenido",
    "tokenServicio"
})
public class ObtenerUrlContenido {

    protected String sesion;
    @XmlElement(name = "eidr_contenido")
    protected String eidrContenido;
    @XmlElement(name = "token_servicio")
    protected String tokenServicio;

    /**
     * Obtiene el valor de la propiedad sesion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSesion() {
        return sesion;
    }

    /**
     * Define el valor de la propiedad sesion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSesion(String value) {
        this.sesion = value;
    }

    /**
     * Obtiene el valor de la propiedad eidrContenido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEidrContenido() {
        return eidrContenido;
    }

    /**
     * Define el valor de la propiedad eidrContenido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEidrContenido(String value) {
        this.eidrContenido = value;
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
