package com.example.senix.diablorip.model;

import java.io.Serializable;

public class Runes implements Serializable {

    private String name;
    private int nivel;
    private String descripcion;

    public Runes(String name, String descripcion,int nivel ) {
        this.name = name;
        this.nivel = nivel;
        this.descripcion = descripcion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
