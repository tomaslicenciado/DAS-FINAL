package ar.edu.ubp.das.hbo.beans;

import java.util.Date;

public class RegistroEstadisticoViewer {
    private Date fecha_inicio;
    private Date fecha_fin;
    private int cant_usuarios_activos;
    private int cant_registros_nuevos;
    
    public RegistroEstadisticoViewer() {
    }

    public RegistroEstadisticoViewer(Date fecha_inicio, Date fecha_fin, int cant_usuarios_activos,
            int cant_registros_nuevos) {
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.cant_usuarios_activos = cant_usuarios_activos;
        this.cant_registros_nuevos = cant_registros_nuevos;
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

    public int getCant_usuarios_activos() {
        return cant_usuarios_activos;
    }

    public void setCant_usuarios_activos(int cant_usuarios_activos) {
        this.cant_usuarios_activos = cant_usuarios_activos;
    }

    public int getCant_registros_nuevos() {
        return cant_registros_nuevos;
    }

    public void setCant_registros_nuevos(int cant_registros_nuevos) {
        this.cant_registros_nuevos = cant_registros_nuevos;
    }
    
}
