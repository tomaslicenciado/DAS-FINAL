package ar.edu.ubp.das.mss.models;

import java.util.Date;

public class RegistroEstadisticoAcceso {
    private int codigo_unico_id;
    private int cant_accesos;
    private Date fecha_inicio;
    private Date fecha_fin;
    
    public RegistroEstadisticoAcceso() {
    }

    public RegistroEstadisticoAcceso(int id_publicidad, int cant_accesos, Date fecha_inicio, Date fecha_fin) {
        this.codigo_unico_id = id_publicidad;
        this.cant_accesos = cant_accesos;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public int getCodigo_unico_id() {
        return codigo_unico_id;
    }

    public void setCodigo_unico_id(int id_publicidad) {
        this.codigo_unico_id = id_publicidad;
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
