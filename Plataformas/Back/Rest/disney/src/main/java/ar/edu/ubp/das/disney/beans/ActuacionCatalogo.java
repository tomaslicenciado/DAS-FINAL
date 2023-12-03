package ar.edu.ubp.das.disney.beans;

public class ActuacionCatalogo {
    private String nombres;
    private String apellidos;
    private String eidr_contenido;
    public ActuacionCatalogo() {
    }
    public ActuacionCatalogo(String eidr_contenido, String nombres, String apellidos) {
        this.eidr_contenido = eidr_contenido;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }
    public String getEidr_contenido() {
        return eidr_contenido;
    }
    public void setEidr_contenido(String eidr_contenido) {
        this.eidr_contenido = eidr_contenido;
    }
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
