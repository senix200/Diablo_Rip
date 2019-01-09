package com.example.senix.diablorip.model;

import java.io.Serializable;

public class Skills implements Serializable {

    private String name;
    private String imagenURL;
    private int nivel;
    private String descripcion;
    private String clase;

    public Skills(String name, String descripcion,int nivel, String imagenURL, String clase ) {
        this.name = name;
        this.imagenURL = imagenURL;
        this.nivel = nivel;
        this.descripcion = descripcion;
        this.clase = clase;
    }
    public Skills(String name, String descripcion,int nivel, String clase ) {
        this.name = name;
        this.nivel = nivel;
        this.descripcion = descripcion;
        this.clase = clase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String name) {
        this.clase = clase;
    }
}
