package com.example.tunegocio.Models;

public class Categoria {
    private String nombreCategoria;

    public Categoria() {
    }

    public Categoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    @Override
    public String toString() {
        return nombreCategoria;
    }
}

