package ar.edu.ubp.das.sietesentidos.app;

import ar.edu.ubp.das.sietesentidos.platforms.SietesentidosWS;
import jakarta.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        SietesentidosWS implementor = new SietesentidosWS();
        String address = "http://localhost:8081/sietesentidos";
        Endpoint.publish(address, implementor);
    }
}
