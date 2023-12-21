package ar.edu.ubp.das.jpg.app;

import ar.edu.ubp.das.jpg.platforms.JPGWS;
import jakarta.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        JPGWS implementor = new JPGWS();
        String address = "http://localhost:8082/jpg";
        Endpoint.publish(address, implementor);
    }
}
