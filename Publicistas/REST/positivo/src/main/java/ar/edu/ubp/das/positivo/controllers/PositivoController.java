package ar.edu.ubp.das.positivo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ar.edu.ubp.das.positivo.beans.RespuestaBean;
import ar.edu.ubp.das.positivo.repositories.PositivoRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping(
    path = "/positivo",
    produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class PositivoController {
    @Autowired
    PositivoRepository repo;

    @PostMapping(
        path = "/obtenerPublicidades",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerPublicidades(@RequestBody Map<String, String> body) {
        return new ResponseEntity<RespuestaBean>(repo.obtenerPublicidades(body.get("token_servicio")), HttpStatus.OK);
    }
}
