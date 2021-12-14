package com.example.tunegocio.Models;

public class Unidad {
    String nombreUnidad;

    public Unidad() {
    }

    public Unidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    @Override
    public String toString() {
        return nombreUnidad;
    }
}
