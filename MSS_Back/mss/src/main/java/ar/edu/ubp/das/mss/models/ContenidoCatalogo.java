package ar.edu.ubp.das.mss.models;

import java.util.Date;

public class ContenidoCatalogo {
    private String eidr_contenido;
    private String titulo;
    private String url_imagen;
    private String descripcion;
    private Date fecha_estreno;
    private String genero;
    private String pais;
    private String tipo_contenido;
    private boolean destacado;
    private Date fecha_Carga;
    
    public ContenidoCatalogo(){}

    public ContenidoCatalogo(String eidr, String titulo, String url_imagen, String descripcion, Date fecha_estreno,
            String genero, String pais, String tipo_contenido, boolean destacado, Date fecha_Carga) {
        this.eidr_contenido = eidr;
        this.titulo = titulo;
        this.url_imagen = url_imagen;
        this.descripcion = descripcion;
        this.fecha_estreno = fecha_estreno;
        this.genero = genero;
        this.pais = pais;
        this.tipo_contenido = tipo_contenido;
        this.fecha_Carga = fecha_Carga;
        this.destacado = destacado;
    }

    public boolean isDestacado() {
        return destacado;
    }

    public void setDestacado(boolean reciente) {
        this.destacado = reciente;
    }

    public Date getFecha_Carga() {
        return fecha_Carga;
    }

    public void setFecha_Carga(Date destacado) {
        this.fecha_Carga = destacado;
    }

    public String getEidr_contenido() {
        return eidr_contenido;
    }
    public void setEidr_contenido(String eidr) {
        this.eidr_contenido = eidr;
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
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
    public String getTipo_contenido() {
        return tipo_contenido;
    }
    public void setTipo_contenido(String tipo_contenido) {
        this.tipo_contenido = tipo_contenido;
    }
}
