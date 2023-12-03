package ar.edu.ubp.das.mss.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ar.edu.ubp.das.mss.models.RespuestaBean;
import ar.edu.ubp.das.mss.repositories.MSSRepository;

@Controller
@RequestMapping(
    path = "/mss",
    produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class MSSController {
    @Autowired
    MSSRepository repo;

    @PostMapping(
        path = "/prueba",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> prueba(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.prueba(Integer.parseInt(body.get("id_login")), body.get("nombres"), body.get("apellidos"),
                     body.get("email"), body.get("password")), HttpStatus.OK);
    }
}
