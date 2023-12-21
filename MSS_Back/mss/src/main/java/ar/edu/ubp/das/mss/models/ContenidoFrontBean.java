package ar.edu.ubp.das.mss.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContenidoFrontBean {
    private String eidr_contenido;
    private String titulo;
    private String url_imagen;
    private String descripcion;
    private Date fecha_estreno;
    private String genero;
    private String tipo_contenido;
    private String pais;
    private List<ActuacionCatalogo> actuaciones;
    private List<DireccionCatalogo> direcciones;
    private List<ContenidoXPlataformaSqlBean> cont_x_plataforma;

    public ContenidoFrontBean() {
        this.actuaciones = new ArrayList<>();
        this.direcciones = new ArrayList<>();
        this.cont_x_plataforma = new ArrayList<>();
    }

    public ContenidoFrontBean(String eidr_contenido, String titulo, String url_imagen, String descripcion,
            Date fecha_estreno, String genero, String tipo_contenido, String pais, List<ActuacionCatalogo> actuaciones,
            List<DireccionCatalogo> direcciones, List<ContenidoXPlataformaSqlBean> cont_x_plataforma) {
        this.eidr_contenido = eidr_contenido;
        this.titulo = titulo;
        this.url_imagen = url_imagen;
        this.descripcion = descripcion;
        this.fecha_estreno = fecha_estreno;
        this.genero = genero;
        this.tipo_contenido = tipo_contenido;
        this.pais = pais;
        this.actuaciones = actuaciones;
        this.direcciones = direcciones;
        this.cont_x_plataforma = cont_x_plataforma;
    }

    public String getEidr_contenido() {
        return eidr_contenido;
    }

    public void setEidr_contenido(String eidr_contenido) {
        this.eidr_contenido = eidr_contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha_estreno() {
        return fecha_estreno;
    }

    public void setFecha_estreno(Date fecha_estreno) {
        this.fecha_estreno = fecha_estreno;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTipo_contenido() {
        return tipo_contenido;
    }

    public void setTipo_contenido(String tipo_contenido) {
        this.tipo_contenido = tipo_contenido;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public List<ActuacionCatalogo> getActuaciones() {
        return actuaciones;
    }

    public void setActuaciones(List<ActuacionCatalogo> actuaciones) {
        this.actuaciones = actuaciones;
    }

    public List<DireccionCatalogo> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<DireccionCatalogo> direcciones) {
        this.direcciones = direcciones;
    }

    public List<ContenidoXPlataformaSqlBean> getCont_x_plataforma() {
        return cont_x_plataforma;
    }

    public void setCont_x_plataforma(List<ContenidoXPlataformaSqlBean> cont_x_plataforma) {
        this.cont_x_plataforma = cont_x_plataforma;
    }
    
    public void agregarActuacion (ActuacionCatalogo actuacion){
        this.actuaciones.add(actuacion);
    }

    public void agregarDireccion (DireccionCatalogo direccion){
        this.direcciones.add(direccion);
    }

    public void agregarContenidoXPlataforma (ContenidoXPlataformaSqlBean cont){
        this.cont_x_plataforma.add(cont);
    }
}
