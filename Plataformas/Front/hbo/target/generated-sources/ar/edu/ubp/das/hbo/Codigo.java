
package ar.edu.ubp.das.hbo;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para codigo.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <pre>
 * &lt;simpleType name="codigo"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="OK"/&gt;
 *     &lt;enumeration value="ERROR"/&gt;
 *     &lt;enumeration value="NO_AUTORIZADO"/&gt;
 *     &lt;enumeration value="NO_ENCONTRADO"/&gt;
 *     &lt;enumeration value="CREADO"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "codigo")
@XmlEnum
public enum Codigo {

    OK,
    ERROR,
    NO_AUTORIZADO,
    NO_ENCONTRADO,
    CREADO;

    public String value() {
        return name();
    }

    public static Codigo fromValue(String v) {
        return valueOf(v);
    }

}
