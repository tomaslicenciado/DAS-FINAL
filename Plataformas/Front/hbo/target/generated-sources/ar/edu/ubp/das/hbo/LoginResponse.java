
package ar.edu.ubp.das.hbo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para loginResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="loginResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="validacion" type="{http://platforms.hbo.das.ubp.edu.ar/}respuestaBean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "loginResponse", propOrder = {
    "validacion"
})
public class LoginResponse {

    protected RespuestaBean validacion;

    /**
     * Obtiene el valor de la propiedad validacion.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaBean }
     *     
     */
    public RespuestaBean getValidacion() {
        return validacion;
    }

    /**
     * Define el valor de la propiedad validacion.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaBean }
     *     
     */
    public void setValidacion(RespuestaBean value) {
        this.validacion = value;
    }

}
