package org.xandr.domain;

import net.datafaker.Faker;

import java.util.Random;

public class Elemento {
    private String id;
    private String palabra;
    private String categoria;

    public Elemento(String id, String palabra, String categoria) {
        this.id = id;
        this.palabra = palabra;
        this.categoria = categoria;
    }

    public Elemento() {
        Random random = new Random();
        this.id = String.valueOf(random.nextInt(100));
        this.palabra = new Faker().pokemon().name();
        this.categoria = "pokemon";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
