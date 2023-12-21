
package ar.edu.ubp.das.hbo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerTokenViewer complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerTokenViewer"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="transaction_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "obtenerTokenViewer", propOrder = {
    "transactionId",
    "tokenServicio"
})
public class ObtenerTokenViewer {

    @XmlElement(name = "transaction_id")
    protected String transactionId;
    @XmlElement(name = "token_servicio")
    protected String tokenServicio;

    /**
     * Obtiene el valor de la propiedad transactionId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Define el valor de la propiedad transactionId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionId(String value) {
        this.transactionId = value;
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
