package com.example.tunegocio.Models;

public class Producto {
    private String txt, nombreproducto, unidad, categoria, descripcion, proveedor;
    //private float precioCompra, precioVenta;

    public Producto() {
    }

    public Producto(String txt) {
        this.txt = txt;
        //this.nombreproducto = nombreproducto;
        //this.unidad = unidad;
        ///this.categoria = categoria;
        //this.descripcion = descripcion;
        //this.proveedor = proveedor;
        //this.precioCompra = precioCompra;
        //this.precioVenta = precioVenta;
    }

    public String getTxt() {
        return txt;
    }
/*
    public String getNombreproducto() {
        return nombreproducto;
    }

    public String getUnidad() {
        return unidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getProveedor() {
        return proveedor;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }
*/
    public void setTxt(String txt) {
        this.txt = txt;
    }
/*
    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

 */
}
