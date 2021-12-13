package com.example.tunegocio.Models;

public class Proveedor {
    public String nombreCompleto;
    public String telefonoUno;
    public String telefonoDos;
    public String nombreEmpresa;
    public String correo;
    public String descripcion;

    public Proveedor() {
    }

    public Proveedor(String nombreCompleto, String telefonoUno, String telefonoDos, String nombreEmpresa, String correo, String descripcion) {
        this.nombreCompleto = nombreCompleto;
        this.telefonoUno = telefonoUno;
        this.telefonoDos = telefonoDos;
        this.nombreEmpresa = nombreEmpresa;
        this.correo = correo;
        this.descripcion = descripcion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTelefonoUno() {
        return telefonoUno;
    }

    public String getTelefonoDos() {
        return telefonoDos;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setTelefonoUno(String telefonoUno) {
        this.telefonoUno = telefonoUno;
    }

    public void setTelefonoDos(String telefonoDos) {
        this.telefonoDos = telefonoDos;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
