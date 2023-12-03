package ar.edu.ubp.das.hbo.app;

import ar.edu.ubp.das.hbo.platforms.HBOWS;
import jakarta.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        HBOWS implementor = new HBOWS();
        String address = "http://localhost:8084/hbo";
        Endpoint.publish(address, implementor);
    }
}
