package org.xandr.service;

import org.xandr.common.Constantes;
import org.xandr.dao.DaoElementos;
import org.xandr.dao.DaoElementosImplementacion;
import org.xandr.domain.Elemento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionElementosImplementacion implements GestionElementos {
    private DaoElementos daoElementos;

    public GestionElementosImplementacion(DaoElementos daoElementos) {
        this.daoElementos = daoElementos;
    }

    public GestionElementosImplementacion() {
        this.daoElementos = new DaoElementosImplementacion();
    }

    @Override
    public boolean isEmptyElementosList() {
        return daoElementos.isEmptyElementosList();
    }

    @Override
    public List<Elemento> getListaElementos() {
        return daoElementos.getElementos();
    }

    @Override
    public boolean insertarElemento(Elemento elemento) {
        return daoElementos.insertarElemento(elemento);
    }

    @Override
    public String getPalabraAdivinar(String categoria) {
        return daoElementos.getPalabraAdivinar(categoria);
    }

    @Override
    public List<Elemento> listar(String categoria) {
        return daoElementos.getElementosCategoria(categoria);
    }

    @Override
    public List<Elemento> listarElementos(boolean ascendente) {
        return daoElementos.listadoOrdenado(ascendente);
    }

    @Override
    public boolean modificarElemento(String id, String incognita) {
        try {
            int idInt = Integer.parseInt(id);
            return daoElementos.modificarElemento(idInt, incognita);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public List<Elemento> getListaElementosCategoria(String categoria) {
        return daoElementos.getElementosCategoria(categoria);
    }

    @Override
    public void eliminarElemento(Elemento elemento) {
        daoElementos.eliminarElemento(elemento);
    }

    @Override
    public boolean eliminarElemento(String id) {
        try {
            int idInt = Integer.parseInt(id);
            return daoElementos.eliminarElemento(idInt);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void vaciarDiccionario() {
        daoElementos.vaciarDiccionario();
    }

    @Override
    public void crearFicheros() throws IOException {

    }

    @Override
    public boolean cargarFichero() throws IOException {
        File fichero = new File(Constantes.NOMBRE_FICHERO);
        if (!fichero.exists() || fichero.length() == 0) {
            return false;
        }

        List<Elemento> elementos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    String id = partes[0];
                    String palabra = partes[1];
                    String categoria = partes[2];
                    elementos.add(new Elemento(id, palabra, categoria));
                }
            }
        }

        daoElementos = new DaoElementosImplementacion(new org.xandr.dao.Elementos(elementos));
        return true;
    }

    @Override
    public boolean escribirFichero() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constantes.NOMBRE_FICHERO))) {
            for (Elemento elemento : daoElementos.getElementos()) {
                String linea = elemento.getId() + ";" + elemento.getPalabra() + ";" + elemento.getCategoria();
                writer.write(linea);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("HA OCURRIDO UN ERROR AL ESCRIBIR EL FICHERO: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Elemento getElementoAdivinar(String categoria) {
        List<Elemento> elementos = daoElementos.getElementosCategoria(categoria);
        if (elementos.isEmpty()) return null;
        int index = (int) (Math.random() * elementos.size());
        return elementos.get(index);
    }

    @Override
    public boolean escribirFicheroBinario() {
        return false;
    }

    @Override
    public boolean cargarFicheroBinario() {
        return false;
    }
}
