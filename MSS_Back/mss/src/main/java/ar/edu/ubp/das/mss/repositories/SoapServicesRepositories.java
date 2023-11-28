package ar.edu.ubp.das.mss.repositories;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import jakarta.xml.soap.*;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.namespace.QName;
//import javax.xml.soap.*;

public class SoapServicesRepositories {

    // Base URL for REST services
    private static final String REST_BASE_URL = "http://example.com/rest";

    // WSDL URL for SOAP service
    private static final String SOAP_WSDL_URL = "http://example.com/soap?wsdl";

    // Service and port names for SOAP service
    private static final QName SOAP_SERVICE_NAME = new QName("http://example.com/soap", "SoapService");
    private static final QName SOAP_PORT_NAME = new QName("http://example.com/soap", "SoapPort");

    private RestTemplate restTemplate;

    public void WebServiceHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Method to consume REST service
    public String consumeRestService(String path, HttpHeaders headers) {
        String url = REST_BASE_URL + path;
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            // Handle error response
            return null;
        }
    }

    // Method to consume SOAP service
    public String consumeSoapService(String tokenServicio, String transactionId, String urlRetorno) throws SOAPException, IOException {
        // Build SOAP request
        String soapRequest = buildSoapRequest(tokenServicio, transactionId, urlRetorno);

        // Create SOAP connection and send request
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        SOAPMessage soapResponse = soapConnection.call(createSoapMessage(soapRequest), SOAP_WSDL_URL);

        // Parse SOAP response
        return parseSoapResponse(soapResponse);
    }

    private String buildSoapRequest(String tokenServicio, String transactionId, String urlRetorno) throws SOAPException, IOException {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage soapMessage = factory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // Replace "your_namespace" with the actual namespace of the SOAP request
        String namespace = "http://www.example.com/your_namespace";

        // Create the SOAP envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("ns", namespace);

        // Create the SOAP body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement requestElement = soapBody.addChildElement("YourRequestElement", "ns");
        requestElement.addChildElement("tokenServicio").setTextContent(tokenServicio);
        requestElement.addChildElement("transactionId").setTextContent(transactionId);
        requestElement.addChildElement("urlRetorno").setTextContent(urlRetorno);

        // Convert the SOAP message to a string
        return soapMessageToString(soapMessage);
    }

    private SOAPMessage createSoapMessage(String soapRequest) throws SOAPException, IOException {
        MessageFactory factory = MessageFactory.newInstance();
        return factory.createMessage(null, new ByteArrayInputStream(soapRequest.getBytes()));
    }

    private String parseSoapResponse(SOAPMessage soapResponse) throws SOAPException {
        SOAPPart soapPart = soapResponse.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody soapBody = envelope.getBody();

        // Replace "your_namespace" with the actual namespace of the SOAP response
        String namespace = "http://www.example.com/your_namespace";

        // Assuming the response has an element called "LoginURL" that contains the URL
        QName loginURLQName = new QName(namespace, "LoginURL");

        // Search for the element in the response body
        return soapBody.getElementsByTagNameNS(namespace, "LoginURL").item(0).getTextContent();
    }

    private String soapMessageToString(SOAPMessage soapMessage) throws SOAPException, IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapMessage.writeTo(out);
        return new String(out.toByteArray());
    }
}



/*import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.Node;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPPart;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class SoapServicesRepositories {
    public String get_url_login_plataforma(int id_plataforma, String transaction_id, String url_retorno)
            throws Exception {
        String token_servicio = "get_token_servicio(id_plataforma)";
        String url = "";

        if (id_plataforma == 1) {
            url = consumeSoapService(token_servicio, transaction_id, url_retorno);
        } else {
            url = consumeRestService(token_servicio, transaction_id, url_retorno);
        }

        return url;
    }

    private String consumeSoapService(String token_servicio, String transaction_id, String url_retorno) throws Exception {
        URL wsdlURL = new URL("http://example.com/netflix?wsdl"); // Replace with the actual Netflix WSDL URL
        QName serviceName = new QName("http://example.com/netflix", "NetflixWSService"); // Replace with the actual QName
        QName portName = new QName("http://example.com/netflix", "NetflixWSPort"); // Replace with the actual QName

        Service service = Service.create(wsdlURL, serviceName);
        Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

        String soapRequest = buildSoapRequest(token_servicio, transaction_id, url_retorno);
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(soapRequest.getBytes()));

        SOAPMessage soapResponse = dispatch.invoke(soapMessage);

        // Parse the SOAP response to get the URL
        // Assuming the response is in a specific format, you'll need to customize this part
        String url = parseSoapResponse(soapResponse);

        return url;
    }

    private String consumeRestService(String token_servicio, String transaction_id, String url_retorno) {
        String url_servicio = "http://example.com/restservice"; // Replace with the actual URL of the REST service

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url_servicio + "/get_url_login")
                .queryParam("token_servicio", token_servicio)
                .queryParam("transaction_id", transaction_id)
                .queryParam("url_retorno", url_retorno);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(String.class); // Assuming the response is a JSON string containing the URL
        } else {
            // Handle the error response
            return null;
        }
    }

    private String buildSoapRequest(String token_servicio, String transaction_id, String url_retorno) throws SOAPException, IOException {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage soapMessage = factory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // Replace "your_namespace" with the actual namespace of the SOAP request
        // Example: String namespace = "http://www.example.com/your_namespace";
        String namespace = "http://www.example.com/your_namespace";

        // Create the SOAP envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("ns", namespace);

        // Create the SOAP body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement requestElement = soapBody.addChildElement("YourRequestElement", "ns"); // Replace "YourRequestElement" with the actual element name
        requestElement.addChildElement("token_servicio").setTextContent(token_servicio);
        requestElement.addChildElement("transaction_id").setTextContent(transaction_id);
        requestElement.addChildElement("url_retorno").setTextContent(url_retorno);

        // Convert the SOAP message to a string
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        soapMessage.writeTo(outputStream);
        return outputStream.toString();
    }

    private String parseSoapResponse(SOAPMessage soapResponse) throws SOAPException {
        SOAPPart soapPart = soapResponse.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody soapBody = envelope.getBody();

        // Replace "your_namespace" with the actual namespace of the SOAP response
        // Example: String namespace = "http://www.example.com/your_namespace";
        String namespace = "http://www.example.com/your_namespace";

        // Assuming the response has an element called "LoginURL" that contains the URL
        // Replace "LoginURL" with the actual element name
        QName loginURLQName = new QName(namespace, "LoginURL");

        // Search for the element in the response body
        Iterator<Node> elementIterator = soapBody.getChildElements(loginURLQName);
        if (elementIterator.hasNext()) {
            SOAPElement loginURLElement = (SOAPElement) elementIterator.next();
            return loginURLElement.getTextContent();
        } else {
            // Element not found in the response
            return null;
        }
    }
}
*/