package com.example.tunegocio.Models;

public class Venta {
    public String hash;
    public String folio;
    public String fechaVenta;
    public String cantidad;
    public String total;
    public String estado;

    public Venta() {
    }

    public Venta(String hash, String folio, String fechaVenta, String cantidad, String total, String estado) {
        this.hash = hash;
        this.folio = folio;
        this.fechaVenta = fechaVenta;
        this.cantidad = cantidad;
        this.total = total;
        this.estado = estado;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
