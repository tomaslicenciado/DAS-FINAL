package ar.edu.ubp.das.mss.models;

public class PlataformaAmpliadaBean {
    private int id_plataforma;
    private String nombre;
    private String url_icono;

    
    public PlataformaAmpliadaBean() {
    }

    public PlataformaAmpliadaBean(int id_plataforma, String nombre, String url_icono) {
        this.id_plataforma = id_plataforma;
        this.nombre = nombre;
        this.url_icono = url_icono;
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
}
