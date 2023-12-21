package ar.edu.ubp.das.sietesentidos.beans;

public class Publicidad {
    private int banner_code;
    private String url_imagen;
    private String url_contenido;
    private int codigo_unico_id;
    
    public Publicidad() {
    }
    
    public Publicidad(int banner_code, String url_imagen, String url_redirect, int codigo_unico_id) {
        this.banner_code = banner_code;
        this.url_imagen = url_imagen;
        this.url_contenido = url_redirect;
        this.codigo_unico_id = codigo_unico_id;
    }

    public int getBanner_code() {
        return banner_code;
    }
    public void setBanner_code(int banner_code) {
        this.banner_code = banner_code;
    }
    public String getUrl_imagen() {
        return url_imagen;
    }
    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }
    public String getUrl_contenido() {
        return url_contenido;
    }
    public void setUrl_contenido(String url_redirect) {
        this.url_contenido = url_redirect;
    }
    public int getCodigo_unico_id() {
        return codigo_unico_id;
    }
    public void setCodigo_unico_id(int codigo_unico_id) {
        this.codigo_unico_id = codigo_unico_id;
    }
    
}
