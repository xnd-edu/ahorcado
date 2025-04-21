package org.xandr.service;

import org.xandr.domain.Elemento;

import java.io.IOException;
import java.util.List;

public interface GestionElementos {
    public boolean isEmptyElementosList();
    public List<Elemento> getListaElementos();
    public boolean insertarElemento(Elemento Elemento);
    public String getPalabraAdivinar(String categoria);
    public List<Elemento> listar(String categoria);
    public List<Elemento> listarElementos(boolean ascendente);
    public boolean modificarElemento(String id, String incognita);
    public List<Elemento> getListaElementosCategoria(String categoria);
    public void eliminarElemento(Elemento Elemento);

    public void vaciarDiccionario();
    public void crearFicheros()throws IOException;
    public boolean cargarFichero() throws IOException;
    public boolean escribirFichero();
    public Elemento getElementoAdivinar(String categoria);
    public boolean escribirFicheroBinario();
    public boolean cargarFicheroBinario();
    public boolean eliminarElemento(String id);
}
