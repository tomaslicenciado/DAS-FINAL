package ar.edu.ubp.das.prime.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ar.edu.ubp.das.prime.beans.RespuestaBean;
import ar.edu.ubp.das.prime.repositories.PrimeRepository;

@Controller
@RequestMapping(
    path = "/prime",
    produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class PrimeController {
    @Autowired
    PrimeRepository repo;

    @PostMapping(
        path = "/obtenerLoginUrl",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerLoginUrl(@RequestBody Map<String, String> body){ 
        return new ResponseEntity<RespuestaBean>(repo.obtenerLoginUrl(body.get("url_retorno"), body.get("token_servicio")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/obtenerTokenViewer",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerTokenViewer(@RequestBody Map<String, String> body){ 
        return new ResponseEntity<RespuestaBean>(repo.obtenerTokenViewer(body.get("transaction_id"), body.get("token_servicio")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/esUsuarioNuevo",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> esUsuarioNuevo(@RequestBody Map<String, String> body){ 
        return new ResponseEntity<RespuestaBean>(repo.esUsuarioNuevo(body.get("transaction_id"), body.get("token_servicio")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/obtenerCatalogo",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerCatalogo(@RequestBody Map<String, String> body){ 
        return new ResponseEntity<RespuestaBean>(repo.obtenerCatalogo(body.get("sesion"), body.get("token_servicio")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/obtenerSesion",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerSesion(@RequestBody Map<String, String> body){ 
        return new ResponseEntity<RespuestaBean>(repo.obtenerSesion(body.get("token_viewer"), body.get("token_servicio")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/obtenerUrlContenido",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerUrlContenido(@RequestBody Map<String, String> body){ 
        return new ResponseEntity<RespuestaBean>(repo.obtenerUrlContenido(body.get("sesion"), body.get("eidr_contenido"), body.get("token_servicio")), HttpStatus.OK);
    }

    @PostMapping(
        path = "/login",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> login(@RequestBody Map<String, String> body){ 
        return new ResponseEntity<RespuestaBean>(repo.login(Integer.parseInt(body.get("id_login")), body.get("email"), body.get("password")), HttpStatus.OK);
    }

    @PostMapping(
        path = "/register",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> register(@RequestBody Map<String, String> body){ 
        return new ResponseEntity<RespuestaBean>(repo.register(Integer.parseInt(body.get("id_login")), body.get("nombres"), 
                        body.get("apellidos"), body.get("email"), body.get("password")), HttpStatus.OK);
    }

    @PostMapping(
        path = "/insertarEstadisticas",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> insertarEstadisticas(@RequestBody Map<String, String> body){ 
        return new ResponseEntity<RespuestaBean>(repo.insertarEstadisticas(body.get("token_servicio"), body.get("estadisticas_viewers_json"),
                        body.get("estadisticas_contenidos_json")), HttpStatus.OK);
    }
}
