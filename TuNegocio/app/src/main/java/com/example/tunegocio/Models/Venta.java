package com.example.tunegocio.Models;

public class Venta {
    public String fechaVenta;
    public String cantidad;
    public String subtotal;
    public String total;

    public Venta() {
    }

    public Venta(String fechaVenta, String cantidad, String subtotal, String total) {
        this.fechaVenta = fechaVenta;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.total = total;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public String getTotal() {
        return total;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
