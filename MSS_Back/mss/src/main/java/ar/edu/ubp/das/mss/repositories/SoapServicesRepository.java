package ar.edu.ubp.das.mss.repositories;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import ar.edu.ubp.das.mss.models.Codigo;
import ar.edu.ubp.das.mss.models.RespuestaBean;

public class SoapServicesRepository {
    public static RespuestaBean SoapCall(String wsdlUrlStr, String operationName, Map<String, String> parameters) throws Exception{
        // Dirección del servicio SOAP
        String endpoint = wsdlUrlStr.replaceAll("wsdl", "").replace("?", "");
        String namespace = getNamespace(wsdlUrlStr);

        StringBuilder soapXml = new StringBuilder("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:web=\""+namespace+"\"><soapenv:Header/><soapenv:Body>" +
                    "<web:" + operationName + ">");
    
            for (Map.Entry<String, String> param : parameters.entrySet()) {
                soapXml.append("<").append(param.getKey()).append(">").append(param.getValue()).append("</").append(param.getKey()).append(">");
            }
    
            soapXml.append("</web:").append(operationName).append("></soapenv:Body></soapenv:Envelope>");
        
        // Configuración de la conexión
        URL url = new URI(endpoint).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        connection.setDoOutput(true);

        // Escribir el contenido del mensaje SOAP en la solicitud
        try (OutputStream os = connection.getOutputStream(); OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
            osw.write(soapXml.toString());
            osw.flush();
        }

        // Obtener la respuesta
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Leer la respuesta
            try (InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                    BufferedReader br = new BufferedReader(isr)) {

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                // Imprimir la respuesta
                return extractSoapResponseInfo(response.toString());
            }
        } else {
            throw new Exception("Error: " + responseCode);
        }
    }

    private static String getNamespace(String wsdlUrl) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(wsdlUrl);

        Element definitionsElement = document.getDocumentElement();
        String targetNamespace = definitionsElement.getAttribute("targetNamespace");

        return targetNamespace;
    }
    
    private static RespuestaBean extractSoapResponseInfo(String soapResponse) throws Exception{
        try {
            // Configurar el analizador DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parsear la respuesta SOAP como un documento DOM
            Document document = builder.parse(new InputSource(new StringReader(soapResponse)));

            // Encontrar el elemento <validacion>
            NodeList soapResponseBodyNodes = document.getElementsByTagName("soap:Body");
            if (soapResponseBodyNodes.getLength() > 0) {
                Element soapResponseBodyElement = (Element) soapResponseBodyNodes.item(0);

                // Extraer información de <validacion>
                String body = getTextContent(soapResponseBodyElement, "body");
                String mensaje = getTextContent(soapResponseBodyElement, "mensaje");
                String status = getTextContent(soapResponseBodyElement, "status");

                // Almacenar la información en el Map
                return new RespuestaBean(Codigo.valueOf(status),mensaje, body);
            }
            return new RespuestaBean(Codigo.ERROR, "La respuesta del servicio no contiene una respuesta válida", soapResponse);

        } catch (Exception e) {
            throw e;
        }
    }

    private static String getTextContent(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}
