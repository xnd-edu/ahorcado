package org.xandr.dao;

import org.xandr.domain.Elemento;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoElementosImplementacion implements DaoElementos {
    private Elementos lista;

    public DaoElementosImplementacion(Elementos lista) {
        this.lista = lista;
    }

    public DaoElementosImplementacion() {
        this.lista = new Elementos();
    }

    @Override
    public boolean isEmptyElementosList() {
        return lista.getListaElementos().isEmpty();
    }

    @Override
    public boolean insertarElemento(Elemento nuevo) {
        for (Elemento e : lista.getListaElementos()) {
            // Verificar si existe un elemento con ese ID
            if (e.getId().equals(nuevo.getId())) {
                return false;
            }
        }
        return lista.getListaElementos().add(nuevo);
    }

    @Override
    public boolean insertarElemento(int id, String palabra, String categoria) {
        Elemento nuevo = new Elemento(String.valueOf(id), palabra, categoria);
        return lista.insertarElemento(nuevo);
    }

    @Override
    public List<Elemento> getElementos() {
        return lista.getListaElementos();
    }

    @Override
    public List<Elemento> getElementosCategoria(String categoria) {
        return lista.getListaElementosCategoria(categoria);
    }

    @Override
    public List<Elemento> listadoOrdenado(boolean ascendente) {
        List<Elemento> listaElementos = lista.getListaElementos();
        listaElementos.sort((e1, e2) -> e1.getPalabra().compareToIgnoreCase(e2.getPalabra())); // Orden ascendente
        return listaElementos;
    }

    @Override
    public boolean modificarCategoria(int id, String categoria) {
        return false;
    }

    @Override
    public boolean modificarElemento(int id, String nuevaPalabra) {
        Optional<Elemento> elementoOpt = lista.getListaElementos().stream()
                .filter(e -> e.getId().equals(String.valueOf(id)))
                .findFirst();
        if (elementoOpt.isPresent()) {
            elementoOpt.get().setPalabra(nuevaPalabra);
            return true;
        }
        return false;
    }

    @Override
    public void eliminarElemento(Elemento elemento) {
        lista.getListaElementos().remove(elemento);
    }

    @Override
    public boolean eliminarElemento(int id) {
        return lista.getListaElementos().removeIf(e -> e.getId().equals(String.valueOf(id)));
    }

    @Override
    public void vaciarDiccionario() {
        lista.vaciarListaElementos(new ArrayList<>());
    }

    @Override
    public String getPalabraAdivinar(String categoria) {
        List<Elemento> elementosCategoria = lista.getListaElementosCategoria(categoria);
        if (elementosCategoria.isEmpty()) {
            return null;
        }
        int indice = (int) (Math.random() * elementosCategoria.size());
        return elementosCategoria.get(indice).getPalabra();
    }

}
