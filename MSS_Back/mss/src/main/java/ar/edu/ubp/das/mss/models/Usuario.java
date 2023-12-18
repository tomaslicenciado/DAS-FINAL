package ar.edu.ubp.das.mss.models;

public class Usuario {
    private int id_usuario;
    private String nombres;
    private String apellidos;
    private int id_nivel;
    private String nivel;
    private String token;
    private boolean validado;
    
    public Usuario() {
    }

    public Usuario(int id_usuario, String nombres, String apellidos, int id_nivel, String nivel, String token, boolean validado) {
        this.id_usuario = id_usuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.id_nivel = id_nivel;
        this.nivel = nivel;
        this.token = token;
        this.validado = validado;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id) {
        this.id_usuario = id;
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

    public int getId_nivel() {
        return id_nivel;
    }

    public void setId_nivel(int id_nivel) {
        this.id_nivel = id_nivel;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }
    
}
