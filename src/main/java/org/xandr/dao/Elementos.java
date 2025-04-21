package org.xandr.dao;

import org.xandr.domain.Elemento;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Elementos {
    private final List<Elemento> listaElementos;

    public Elementos(List<Elemento> listaElementos) {
        this.listaElementos = listaElementos;
    }

    public Elementos() {
        this.listaElementos = new ArrayList<>();
        // Generación de prueba
        Random rnd = new Random();
        String categoria = "pokemon";
        for (int i = 0; i < 10; i++) {
            listaElementos.add(new Elemento());
        }
    }

    public List<Elemento> getListaElementos() {
        return listaElementos;
    }

    public void vaciarListaElementos(List<Elemento> nuevaLista) {
        listaElementos.clear();
        listaElementos.addAll(nuevaLista);
    }

    public boolean insertarElemento(Elemento elemento) {
        return listaElementos.add(elemento);
    }


    public List<Elemento> getListaElementosCategoria(String categoria) {
        if (categoria == null || categoria.isEmpty()) {
            return new ArrayList<>();
        }
        return listaElementos.stream()
                .filter(e -> e.getCategoria() != null && e.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }
}
