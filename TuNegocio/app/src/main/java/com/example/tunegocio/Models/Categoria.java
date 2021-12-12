package com.example.tunegocio.Models;

public class Categoria {
    private String nombreCetegoria;

    public Categoria() {
    }

    public Categoria(String nombreCetegoria) {
        this.nombreCetegoria = nombreCetegoria;
    }

    public String getNombreCetegoria() {
        return nombreCetegoria;
    }

    public void setNombreCetegoria(String nombreCetegoria) {
        this.nombreCetegoria = nombreCetegoria;
    }
}

