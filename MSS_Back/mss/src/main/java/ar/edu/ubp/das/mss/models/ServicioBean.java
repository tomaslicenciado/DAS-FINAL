package ar.edu.ubp.das.mss.models;

public class ServicioBean {
    private String nombre;
    private String url_conexion;
    private int id_tipo;
    private String tipo;
    private String token_servicio;
    
    public ServicioBean() {
    }

    public ServicioBean(String nombre, String url_conexion, int id_tipo, String tipo, String token_servicio) {
        this.nombre = nombre;
        this.url_conexion = url_conexion;
        this.id_tipo = id_tipo;
        this.tipo = tipo;
        this.token_servicio = token_servicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl_conexion() {
        return url_conexion;
    }

    public void setUrl_conexion(String url_conexion) {
        this.url_conexion = url_conexion;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getToken_servicio() {
        return token_servicio;
    }

    public void setToken_servicio(String token_servicio) {
        this.token_servicio = token_servicio;
    }
    
}
