package ar.edu.ubp.das.netflix.beans;

public class DireccionCatalogo {
    private String eidr_contenido;
    private String nombres;
    private String apellidos;
    
    public DireccionCatalogo() {
    }
    public DireccionCatalogo(String eidr_contenido, String nombres, String apellidos) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.eidr_contenido = eidr_contenido;
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
