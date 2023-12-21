package ar.edu.ubp.das.mss.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacturaBean {
    private int id_factura;
    private int id_cliente;
    private Date fecha_facturacion;
    private Date fecha_inicial;
    private Date fecha_final;
    private float monto_total;
    private List<DetalleFacturaBean> detalles;
    
    public FacturaBean() {
        this.detalles = new ArrayList<>();
    }

    public FacturaBean(int id_factura, int id_cliente, Date fecha_facturacion, Date fecha_inicial,
            Date fecha_final, float monto_total, List<DetalleFacturaBean> detalles) {
        this.id_factura = id_factura;
        this.id_cliente = id_cliente;
        this.fecha_facturacion = fecha_facturacion;
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
        this.monto_total = monto_total;
        this.detalles = detalles;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_plataforma) {
        this.id_cliente = id_plataforma;
    }

    public Date getFecha_facturacion() {
        return fecha_facturacion;
    }

    public void setFecha_facturacion(Date fecha_facturacion) {
        this.fecha_facturacion = fecha_facturacion;
    }

    public Date getFecha_inicial() {
        return fecha_inicial;
    }

    public void setFecha_inicial(Date fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    public Date getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(Date fecha_final) {
        this.fecha_final = fecha_final;
    }

    public float getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(float monto_total) {
        this.monto_total = monto_total;
    }

    public List<DetalleFacturaBean> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFacturaBean> detalles) {
        this.detalles = detalles;
    }
}
