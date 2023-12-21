package ar.edu.ubp.das.netflix.beans;

import java.util.LinkedList;
import java.util.List;

public class Catalogo {
    private List<ContenidoCatalogo> contenidos;
    private List<DireccionCatalogo> direcciones;
    private List<ActuacionCatalogo> actuaciones;

    public Catalogo() {
        this.contenidos = new LinkedList<ContenidoCatalogo>();
        this.actuaciones = new LinkedList<ActuacionCatalogo>();
        this.direcciones = new LinkedList<DireccionCatalogo>();
    }
    public Catalogo(List<ContenidoCatalogo> contenidos, List<DireccionCatalogo> direcciones,
            List<ActuacionCatalogo> actuaciones) {
        this.contenidos = contenidos;
        this.direcciones = direcciones;
        this.actuaciones = actuaciones;
    }
    public List<ContenidoCatalogo> getContenidos() {
        return contenidos;
    }
    public void setContenidos(List<ContenidoCatalogo> contenidos) {
        this.contenidos = contenidos;
    }
    public List<DireccionCatalogo> getDirecciones() {
        return direcciones;
    }
    public void setDirecciones(List<DireccionCatalogo> direcciones) {
        this.direcciones = direcciones;
    }
    public List<ActuacionCatalogo> getActuaciones() {
        return actuaciones;
    }
    public void setActuaciones(List<ActuacionCatalogo> actuaciones) {
        this.actuaciones = actuaciones;
    }
    public void addContenido(ContenidoCatalogo contenido){
        this.contenidos.add(contenido);
    }
    public void addDireccion(DireccionCatalogo direccion){
        this.direcciones.add(direccion);
    }
    public void addActuacion(ActuacionCatalogo actuacion){
        this.actuaciones.add(actuacion);
    }
}
