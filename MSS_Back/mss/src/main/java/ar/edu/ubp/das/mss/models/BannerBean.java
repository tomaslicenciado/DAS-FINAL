package ar.edu.ubp.das.mss.models;

public class BannerBean {
    private int banner_code;
    private float tarifa_base;
    private int id_ult_publicidad;
    private float tarifa_uso_exclusivo;
    
    public BannerBean() {
    }
    public BannerBean(int banner_code, float tarifa_base, int id_ult_publicidad, float tarifa_uso_exclusivo) {
        this.banner_code = banner_code;
        this.tarifa_base = tarifa_base;
        this.id_ult_publicidad = id_ult_publicidad;
        this.tarifa_uso_exclusivo = tarifa_uso_exclusivo;
    }
    public int getBanner_code() {
        return banner_code;
    }
    public void setBanner_code(int banner_code) {
        this.banner_code = banner_code;
    }
    public float getTarifa_base() {
        return tarifa_base;
    }
    public void setTarifa_base(float tarifa_base) {
        this.tarifa_base = tarifa_base;
    }
    public int getId_ult_publicidad() {
        return id_ult_publicidad;
    }
    public void setId_ult_publicidad(int id_ult_publicidad) {
        this.id_ult_publicidad = id_ult_publicidad;
    }
    public float getTarifa_uso_exclusivo() {
        return tarifa_uso_exclusivo;
    }
    public void setTarifa_uso_exclusivo(float tarifa_uso_exclusivo) {
        this.tarifa_uso_exclusivo = tarifa_uso_exclusivo;
    }
    
}
