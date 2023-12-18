package ar.edu.ubp.das.mss.models;

public class DetalleFacturaBean {
    private String descripcion;
    private int cantidad;
    private float monto_unitario;
    private float subtotal;
    
    public DetalleFacturaBean() {
    }

    public DetalleFacturaBean(String descripcion, int cantidad, float monto_unitario, float subtotal) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.monto_unitario = monto_unitario;
        this.subtotal = subtotal;
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
