package ar.edu.ubp.das.mss.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ar.edu.ubp.das.mss.models.Codigo;
import ar.edu.ubp.das.mss.models.RespuestaBean;
import ar.edu.ubp.das.mss.repositories.MSSRepository;
import ar.edu.ubp.das.mss.repositories.MSSRepository.Nivel;

@Controller
@RequestMapping(
    path = "/mss",
    produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class MSSController {
    @Autowired
    MSSRepository repo;

    @PostMapping(
        path = "/iniciarSesion",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> iniciarSesion(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.iniciarSesion(body.get("email"), body.get("password")), HttpStatus.OK);
    }

    @PostMapping(
        path = "/registrarSuscriptor",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> registrarSuscriptor(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.registrarSuscriptor(body.get("email"), body.get("password"),
                                                body.get("nombres"),body.get("apellidos"),new Gson().fromJson(body.get("preferencias")
                                                ,new TypeToken<List<Integer>>(){}.getType())), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/iniciarFederacionPlataforma",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> iniciarFederacionPlataforma(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.iniciarFederacionPlataforma(Integer.parseInt(body.get("id_plataforma")), body.get("token_suscriptor"),
                                    body.get("url_retorno")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/finalizarFederacionPlataforma",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> finalizarFederacionPlataforma(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.finalizarFederacionPlataforma(body.get("token_suscriptor"), 
                            Integer.parseInt(body.get("id_plataforma"))), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/desuscribirPlataforma",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> desuscribirPlataforma(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.desuscribirPlataforma(Integer.parseInt(body.get("id_plataforma")), body.get("token_suscriptor")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/obtenerContenido",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerContenido(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.obtenerContenido(body.get("token_suscriptor"), Integer.parseInt(body.get("id_plataforma")), body.get("eidr_contenido")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/obtenerListadoGenerosContenido",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerListadoGenerosContenido(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.obtenerListadoGenerosContenido(body.get("token_usuario")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/obtenerListadoPlataformas",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerListadoPlataformas(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.obtenerListadoPlataformas(body.get("token_usuario")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/obtenerCatalogo",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerCatalogo(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.obtenerCatalogo(body.get("token_suscriptor")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/obtenerPublicidades",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerPublicidades(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.obtenerPublicidades(body.get("token_suscriptor")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/registrarVisualizacion",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> registrarVisualizacion(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.registrarVisualizacion(body.get("token_suscriptor"), Integer.parseInt(body.get("id_plataforma")), body.get("eidr_contenido")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/registrarAccesoPublicidad",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> registrarAccesoPublicidad(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.registrarAccesoPublicidad(body.get("token_suscriptor"), Integer.parseInt(body.get("id_publicidad"))), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/registrarNuevaPlataforma",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> registrarNuevaPlataforma(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.registrarNuevaPlataforma(body.get("token_usuario"), body.get("nombre"), body.get("url_conexion"), 
                                                body.get("url_icono"), body.get("email"), Integer.parseInt(body.get("id_tipo_servicio")), body.get("token_servicio"), 
                                                Float.parseFloat(body.get("tarifa_nuevos_viewers")), Float.parseFloat(body.get("tarifa_viewers_activos"))), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/actualizarPlataforma",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> actualizarPlataforma(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.actualizarPlataforma(body.get("token_usuario"), Integer.parseInt(body.get("id_plataforma")), body.get("nombre"), body.get("url_conexion"), 
                                                body.get("url_icono"), body.get("email"), Integer.parseInt(body.get("id_tipo_servicio")), body.get("token_servicio"), 
                                                Float.parseFloat(body.get("tarifa_nuevos_viewers")), Float.parseFloat(body.get("tarifa_viewers_activos"))), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/eliminarPlataforma",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> eliminarPlataforma(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.eliminarPlataforma(body.get("token_usuario"), Integer.parseInt(body.get("id_plataforma"))), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/modificarBanner",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> modificarBanner(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.modificarBanner(body.get("token_usuario"), Integer.parseInt(body.get("banner_code")), 
                        Float.parseFloat(body.get("tarifa_base")), Float.parseFloat(body.get("tarifa_exclusividad"))), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/agregarPublicista",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> agregarPublicista(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.agregarPublicista(body.get("token_usuario"), body.get("nombre"), body.get("razon_social"), 
                                            body.get("email"), Integer.parseInt(body.get("telefono")), body.get("nombre_contacto"), 
                                            body.get("url_conexion"), body.get("token_servicio"), Integer.parseInt(body.get("id_tipo_servicio"))), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/modificarPublicista",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> modificarPublicista(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.modificarPublicista(body.get("token_usuario"), Integer.parseInt(body.get("id_publicista")), body.get("nombre"), body.get("razon_social"), 
                                            body.get("email"), Integer.parseInt(body.get("telefono")), body.get("nombre_contacto"), Integer.parseInt(body.get("id_tipo_servicio")), 
                                            body.get("url_conexion"), body.get("token_servicio")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/eliminarPublicista",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> eliminarPublicista(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.eliminarPublicista(body.get("token_usuario"), Integer.parseInt(body.get("id_publicista"))), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/agregarPublicidad",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> agregarPublicidad(@RequestBody Map<String, String> body){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date fecha_inicio = null;
        Date fecha_fin = null;
        try {
            fecha_inicio = format.parse(body.get("fecha_inicio"));
            fecha_fin = format.parse(body.get("fecha_fin"));
        } catch (ParseException e) {
            return new ResponseEntity<>(new RespuestaBean(Codigo.ERROR, "Error al formatear la fecha", e.toString()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<RespuestaBean>(repo.agregarPublicidad(body.get("token_usuario"), Integer.parseInt(body.get("id_publicista")), Integer.parseInt(body.get("banner_code")), 
                                                            Boolean.parseBoolean(body.get("exclusiva")), fecha_inicio, fecha_fin), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/modificarPublicidad",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> modificarPublicidad(@RequestBody Map<String, String> body){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date fecha_inicio = null;
        Date fecha_fin = null;
        try {
            fecha_inicio = format.parse(body.get("fecha_inicio"));
            fecha_fin = format.parse(body.get("fecha_fin"));
        } catch (ParseException e) {
            return new ResponseEntity<>(new RespuestaBean(Codigo.ERROR, "Error al formatear la fecha", e.toString()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<RespuestaBean>(repo.modificarPublicidad(body.get("token_usuario"), Integer.parseInt(body.get("id_publicidad")), 
                                                                Boolean.parseBoolean(body.get("exclusiva")), fecha_inicio, fecha_fin), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/eliminarPublicidad",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> eliminarPublicidad(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.eliminarPublicidad(body.get("token_usuario"), Integer.parseInt(body.get("id_publicidad"))), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/obtenerFeesAPagar",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerFeesAPagar(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.obtenerFeesAPagar(body.get("token_usuario")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/modificarDatosPersonales",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> modificarDatosPersonales(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.modificarDatosPersonales(body.get("token_usuario"), body.get("nombres"), body.get("apellidos"), body.get("password")), HttpStatus.OK);
    }
    
    @PostMapping(
        path = "/obtenerListadoPublicistas",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerListadoPublicistas(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.obtenerListadoPublicistas(body.get("token_usuario")), HttpStatus.OK);
    }

    @PostMapping(
        path = "/obtenerListadoTiposServicio",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerListadoTiposServicio(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.obtenerListadoTiposServicio(body.get("token_usuario")), HttpStatus.OK);
    }

    @PostMapping(
        path = "/obtenerListadoBanners",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerListadoBanners(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.obtenerListadoBanners(body.get("token_usuario")), HttpStatus.OK);
    }

    @PostMapping(
        path = "/actualizarPublicidades",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> actualizarPublicidades(@RequestBody Map<String, String> body){
        try {
            int nivel = repo.obtenerNivelUsuario(body.get("token_usuario"));
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.NO_AUTORIZADO, "No tiene permisos",null), HttpStatus.UNAUTHORIZED);
            repo.actualizarPublicidades();
        } catch (Exception e) {
            return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.ERROR, "Error: "+e.getMessage(),e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.OK, "Publicidades actualizadas con éxito", null), HttpStatus.OK);
    }

    @PostMapping(
        path = "/actualizarCatalogo",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> actualizarCatalogo(@RequestBody Map<String, String> body){
        try {
            int nivel = repo.obtenerNivelUsuario(body.get("token_usuario"));
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.NO_AUTORIZADO, "No tiene permisos",null), HttpStatus.UNAUTHORIZED);
            repo.actualizarCatalogo();
        } catch (Exception e) {
            return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.ERROR, "Error: "+e.getMessage(),e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.OK, "Catálogo actualizado con éxito", null), HttpStatus.OK);
    }

    @PostMapping(
        path = "/enviarEstadisticas",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> enviarEstadisticas(@RequestBody Map<String, String> body){
        try {
            int nivel = repo.obtenerNivelUsuario(body.get("token_usuario"));
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.NO_AUTORIZADO, "No tiene permisos",null), HttpStatus.UNAUTHORIZED);
            repo.enviarEstadisticas();
        } catch (Exception e) {
            return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.ERROR, "Error: "+e.getMessage(),e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.OK, "Estadísticas enviadas con éxito", null), HttpStatus.OK);
    }

    @PostMapping(
        path = "/enviarFacturas",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> enviarFacturas(@RequestBody Map<String, String> body){
        try {
            int nivel = repo.obtenerNivelUsuario(body.get("token_usuario"));
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.NO_AUTORIZADO, "No tiene permisos",null), HttpStatus.UNAUTHORIZED);
            repo.enviarFacturas();
        } catch (Exception e) {
            return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.ERROR, "Error: "+e.getMessage(),e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.OK, "Facturas enviadas con éxito", null), HttpStatus.OK);
    }

    @PostMapping(
        path = "/finalizarFederacionesPendientes",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> finalizarFederacionesPendientes(@RequestBody Map<String, String> body){
        try {
            int nivel = repo.obtenerNivelUsuario(body.get("token_usuario"));
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.NO_AUTORIZADO, "No tiene permisos",null), HttpStatus.UNAUTHORIZED);
            repo.finalizarFederacionesPendientes();
        } catch (Exception e) {
            return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.ERROR, "Error: "+e.getMessage(),e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<RespuestaBean>(new RespuestaBean(Codigo.OK, "Federaciones pendientes finalizadas con éxito", null), HttpStatus.OK);
    }

    @PostMapping(
        path = "/obtenerContenidosMasVistos",
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<RespuestaBean> obtenerContenidosMasVistos(@RequestBody Map<String, String> body){
        return new ResponseEntity<RespuestaBean>(repo.obtenerContenidosMasVistos(body.get("token_suscriptor")), HttpStatus.OK);
    }
}
