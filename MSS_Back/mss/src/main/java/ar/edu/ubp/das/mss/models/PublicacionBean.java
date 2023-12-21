package ar.edu.ubp.das.mss.models;


public class PublicacionBean {
    private int codigo_unico_id;
    private int banner_code;
    private String url_imagen;
    private String url_contenido;

    public PublicacionBean() {
    }

    public PublicacionBean(int banner_code, String url_imagen, String url_contenido, int codigo_unico_id) {
        this.banner_code = banner_code;
        this.url_imagen = url_imagen;
        this.url_contenido = url_contenido;
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

    public void setUrl_contenido(String url_contenido) {
        this.url_contenido = url_contenido;
    }

    public int getCodigo_unico_id() {
        return codigo_unico_id;
    }

    public void setCodigo_unico_id(int codigo_unico_id) {
        this.codigo_unico_id = codigo_unico_id;
    }
    
    /*public static PublicacionComparator getComparador(){
        return new PublicacionComparator();
    }*/

    /*private static class PublicacionComparator implements Comparator<PublicacionBean> {
        @Override
        public int compare(PublicacionBean p1, PublicacionBean p2) {
            // Comparar por banner_code
            int comparacionBanner = Integer.compare(p1.getBanner_code(), p2.getBanner_code());
            if (comparacionBanner != 0) {
                return comparacionBanner;
            }

            // Si banner_code es igual, comparar por codigo_unico_id
            return Integer.compare(p1.getCodigo_unico_id(), p2.getCodigo_unico_id());
        }
    }*/
}
