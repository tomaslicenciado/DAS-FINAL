package ar.edu.ubp.das.jpg.beans;

import java.util.Date;

public class RegistroEstadisticoAcceso {
    private int id_publicidad;
    private int cant_accesos;
    private Date fecha_inicio;
    private Date fecha_fin;
    
    public RegistroEstadisticoAcceso() {
    }

    public RegistroEstadisticoAcceso(int id_publicidad, int cant_accesos, Date fecha_inicio, Date fecha_fin) {
        this.id_publicidad = id_publicidad;
        this.cant_accesos = cant_accesos;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public int getId_publicidad() {
        return id_publicidad;
    }

    public void setId_publicidad(int id_publicidad) {
        this.id_publicidad = id_publicidad;
    }

    public int getCant_accesos() {
        return cant_accesos;
    }

    public void setCant_accesos(int cant_accesos) {
        this.cant_accesos = cant_accesos;
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
    
}
