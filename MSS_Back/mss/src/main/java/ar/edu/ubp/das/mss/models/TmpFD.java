package ar.edu.ubp.das.mss.models;

import java.util.Date;

public class TmpFD {
    private int id_factura;
    private int id_cliente;
    private Date fecha_facturacion;
    private Date fecha_inicial;
    private Date fecha_final;
    private float monto_total;
    private String descripcion;
    private int cantidad;
    private float monto_unitario;
    private float subtotal;
    
    public TmpFD() {
    }

    public TmpFD(int id_factura, int id_cliente, Date fecha_facturacion, Date fecha_inicial, Date fecha_final,
            float monto_total, String descripcion, int cantidad, float monto_unitario, float subtotal) {
        this.id_factura = id_factura;
        this.id_cliente = id_cliente;
        this.fecha_facturacion = fecha_facturacion;
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
        this.monto_total = monto_total;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.monto_unitario = monto_unitario;
        this.subtotal = subtotal;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getMonto_unitario() {
        return monto_unitario;
    }

    public void setMonto_unitario(float monto_unitario) {
        this.monto_unitario = monto_unitario;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }
    
}
