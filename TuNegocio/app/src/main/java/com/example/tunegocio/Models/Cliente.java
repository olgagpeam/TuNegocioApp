package com.example.tunegocio.Models;

public class Cliente {
    public String nombreCompleto;
    public String alias;
    public String telefonoUno;
    public String telefonoDos;
    public String correo;
    public String direccion;
    public String descripcion;

    public Cliente(){
    }

    public Cliente(String nombreCompleto, String alias, String telefonoUno, String telefonoDos, String correo, String direccion, String descripcion) {
        this.nombreCompleto = nombreCompleto;
        this.alias = alias;
        this.telefonoUno = telefonoUno;
        this.telefonoDos = telefonoDos;
        this.correo = correo;
        this.direccion = direccion;
        this.descripcion = descripcion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getAlias() {
        return alias;
    }

    public String getTelefonoUno() {
        return telefonoUno;
    }

    public String getTelefonoDos() {
        return telefonoDos;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setTelefonoUno(String telefonoUno) {
        this.telefonoUno = telefonoUno;
    }

    public void setTelefonoDos(String telefonoDos) {
        this.telefonoDos = telefonoDos;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
