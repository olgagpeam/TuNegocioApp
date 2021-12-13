package com.example.tunegocio.Models;

public class Empleado {
    public String nombreCompleto;
    public String fechaNacimiento;
    public String puestoTrabajo;
    public String telefono;
    public String correo;
    public String salario;
    public String descripcion;

    public Empleado() {
    }

    public Empleado(String nombreCompleto, String fechaNacimiento, String puestoTrabajo, String telefono, String correo, String salario, String descripcion) {
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.puestoTrabajo = puestoTrabajo;
        this.telefono = telefono;
        this.correo = correo;
        this.salario = salario;
        this.descripcion = descripcion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getPuestoTrabajo() {
        return puestoTrabajo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getSalario() {
        return salario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setPuestoTrabajo(String puestoTrabajo) {
        this.puestoTrabajo = puestoTrabajo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
