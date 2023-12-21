package ar.edu.ubp.das.mss.models;

public class PublicistaBean {
    private int id_publicista;
    private String nombre;
    private String razon_social;
    private String email;
    private int telefono;
    private String nombre_contacto;
    private int id_tipo_servicio;
    private String url_conexion;
    private String token_servicio;
    
    public PublicistaBean(){}

    public PublicistaBean(int id_publicista, String nombre, String razon_social, String email, int telefono,
            String nombre_contacto, int id_tipo_servicio, String url_conexion, String token_servicio) {
        this.id_publicista = id_publicista;
        this.nombre = nombre;
        this.razon_social = razon_social;
        this.email = email;
        this.telefono = telefono;
        this.nombre_contacto = nombre_contacto;
        this.id_tipo_servicio = id_tipo_servicio;
        this.url_conexion = url_conexion;
        this.token_servicio = token_servicio;
    }

    public int getId_publicista() {
        return id_publicista;
    }

    public void setId_publicista(int id_publicista) {
        this.id_publicista = id_publicista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getNombre_contacto() {
        return nombre_contacto;
    }

    public void setNombre_contacto(String nombre_contacto) {
        this.nombre_contacto = nombre_contacto;
    }

    public int getId_tipo_servicio() {
        return id_tipo_servicio;
    }

    public void setId_tipo_servicio(int id_tipo_servicio) {
        this.id_tipo_servicio = id_tipo_servicio;
    }

    public String getUrl_conexion() {
        return url_conexion;
    }

    public void setUrl_conexion(String url_conexion) {
        this.url_conexion = url_conexion;
    }

    public String getToken_servicio() {
        return token_servicio;
    }

    public void setToken_servicio(String token_servicio) {
        this.token_servicio = token_servicio;
    }

    
}
