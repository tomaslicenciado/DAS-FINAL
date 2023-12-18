package ar.edu.ubp.das.mss.models;

import java.util.Date;

public class ContenidoXPlataformaSqlBean {
    private int id_plataforma;
    private String eidr_contenido;
    private Date fecha_carga;
    private boolean destacado;
    
    public ContenidoXPlataformaSqlBean() {
    }

    public ContenidoXPlataformaSqlBean(int id_plataforma, String eidr_contenido, Date fecha_carga, boolean destacado) {
        this.id_plataforma = id_plataforma;
        this.eidr_contenido = eidr_contenido;
        this.fecha_carga = fecha_carga;
        this.destacado = destacado;
    }

    public int getId_plataforma() {
        return id_plataforma;
    }

    public void setId_plataforma(int id_plataforma) {
        this.id_plataforma = id_plataforma;
    }

    public String getEidr_contenido() {
        return eidr_contenido;
    }

    public void setEidr_contenido(String eidr_contenido) {
        this.eidr_contenido = eidr_contenido;
    }

    public boolean isDestacado() {
        return destacado;
    }

    public void setDestacado(boolean destacado) {
        this.destacado = destacado;
    }

    public Date getFecha_carga() {
        return fecha_carga;
    }

    public void setFecha_carga(Date fecha_carga) {
        this.fecha_carga = fecha_carga;
    }
    
    
}
