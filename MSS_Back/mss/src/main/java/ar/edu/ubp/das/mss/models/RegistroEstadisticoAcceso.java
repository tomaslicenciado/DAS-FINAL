package ar.edu.ubp.das.mss.models;

import java.util.Date;

public class RegistroEstadisticoAcceso {
    private int codigo_unico_id;
    private Date fecha_acceso;
    
    public RegistroEstadisticoAcceso() {
    }

    public RegistroEstadisticoAcceso(int codigo_unico_id, Date fecha_acceso) {
        this.codigo_unico_id = codigo_unico_id;
        this.fecha_acceso = fecha_acceso;
    }

    public int getCodigo_unico_id() {
        return codigo_unico_id;
    }

    public void setCodigo_unico_id(int id_publicidad) {
        this.codigo_unico_id = id_publicidad;
    }

    public Date getFecha_acceso() {
        return fecha_acceso;
    }

    public void setFecha_acceso(Date fecha_acceso) {
        this.fecha_acceso = fecha_acceso;
    }
    
}
