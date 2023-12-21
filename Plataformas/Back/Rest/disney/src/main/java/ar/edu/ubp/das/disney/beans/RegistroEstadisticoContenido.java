package ar.edu.ubp.das.disney.beans;

import java.util.Date;

public class RegistroEstadisticoContenido {
    private String eidr_contenido;
    private Date fecha_inicio;
    private Date fecha_fin;
    private int cant_visualizaciones;
    private int cant_likes;
    
    public RegistroEstadisticoContenido() {
    }

    public RegistroEstadisticoContenido(String eidr_contenido, Date fecha_inicio, Date fecha_fin,
            int cant_visualizaciones, int cant_likes) {
        this.eidr_contenido = eidr_contenido;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.cant_visualizaciones = cant_visualizaciones;
        this.cant_likes = cant_likes;
    }

    public String getEidr_contenido() {
        return eidr_contenido;
    }

    public void setEidr_contenido(String eidr_contenido) {
        this.eidr_contenido = eidr_contenido;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public int getCant_visualizaciones() {
        return cant_visualizaciones;
    }

    public void setCant_visualizaciones(int cant_visualizaciones) {
        this.cant_visualizaciones = cant_visualizaciones;
    }

    public int getCant_likes() {
        return cant_likes;
    }

    public void setCant_likes(int cant_likes) {
        this.cant_likes = cant_likes;
    }
    
}
