package ar.edu.ubp.das.mss.models;

public class PlataformaBean {
    private int id_plataforma;
    private String nombre;
    private String url_icono;
    private String url_conexion;
    private String token_servicio;
    private int id_tipo_servicio;
    private float tarifa_nuevo_viewer;
    private float tarifa_viewer_activo;

    public PlataformaBean() {
    }

    public PlataformaBean(int id_plataforma, String nombre, String url_icono, String url_conexion,
            String token_servicio, int id_tipo_servicio, float tarifa_nuevo_viewer, float tarifa_viewer_activo) {
        this.id_plataforma = id_plataforma;
        this.nombre = nombre;
        this.url_icono = url_icono;
        this.url_conexion = url_conexion;
        this.token_servicio = token_servicio;
        this.id_tipo_servicio = id_tipo_servicio;
        this.tarifa_nuevo_viewer = tarifa_nuevo_viewer;
        this.tarifa_viewer_activo = tarifa_viewer_activo;
    }

    public int getId_plataforma() {
        return id_plataforma;
    }

    public void setId_plataforma(int id_plataforma) {
        this.id_plataforma = id_plataforma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl_icono() {
        return url_icono;
    }

    public void setUrl_icono(String url_icono) {
        this.url_icono = url_icono;
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

    public int getId_tipo_servicio() {
        return id_tipo_servicio;
    }

    public void setId_tipo_servicio(int id_tipo_servicio) {
        this.id_tipo_servicio = id_tipo_servicio;
    }

    public float getTarifa_nuevo_viewer() {
        return tarifa_nuevo_viewer;
    }

    public void setTarifa_nuevo_viewer(float tarifa_nuevo_viewer) {
        this.tarifa_nuevo_viewer = tarifa_nuevo_viewer;
    }

    public float getTarifa_viewer_activo() {
        return tarifa_viewer_activo;
    }

    public void setTarifa_viewer_activo(float tarifa_viewer_activo) {
        this.tarifa_viewer_activo = tarifa_viewer_activo;
    }
}
