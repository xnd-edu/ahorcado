package org.xandr.ui;

import org.xandr.common.CategoriaInvException;
import org.xandr.common.Constantes;
import org.xandr.domain.Elemento;
import org.xandr.service.GestionElementos;
import org.xandr.service.GestionElementosImplementacion;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestionElementos service = new GestionElementosImplementacion();

        try {
            boolean cargado = service.cargarFichero();
            if (cargado) {
                System.out.println("Diccionario cargado correctamente.");
            } else {
                System.out.println("No se encontró fichero, generando diccionario de ejemplo...");
            }
        } catch (IOException e) {
            System.err.println("Error cargando fichero: " + e.getMessage());
        }

        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            int opcion = -1;
            boolean valido = false;

            while (!valido) {
                try {
                    System.out.println("\n--- MENÚ PRINCIPAL ---");
                    System.out.println("1. Jugar al Ahorcado");
                    System.out.println("2. Gestionar Diccionario");
                    System.out.println("0. Salir");

                    System.out.print("Elige una opción: ");
                    opcion = sc.nextInt();
                    valido = true;
                    sc.nextLine(); // Limpiar el buffer
                } catch (InputMismatchException e) {
                    System.out.println("Debes introducir un número, no una letra.");
                    sc.nextLine(); // Limpiar el buffer
                }
            }

            switch (opcion) {
                case 1:
                    System.out.print("Introduce la categoría para jugar: ");
                    String categoria = sc.nextLine();
                    Elemento palabraElegida = service.getElementoAdivinar(categoria);
                    if (palabraElegida != null) {
                        Juego juego = new Juego(palabraElegida);
                        juego.jugar(sc);
                    } else {
                        throw new CategoriaInvException();
                    }
                    break;
                case 2:
                    if (verificarContra(sc)) {
                        boolean salirGestion = false;
                        while (!salirGestion) {
                            System.out.println("\n--- GESTIÓN DEL DICCIONARIO ---");
                            System.out.println("1. Listar elementos ordenados");
                            System.out.println("2. Insertar elemento");
                            System.out.println("3. Modificar elemento");
                            System.out.println("4. Eliminar elemento");
                            System.out.println("5. Vaciar diccionario");
                            System.out.println("0. Volver");

                            System.out.print("Opción: ");
                            String opGestion = sc.nextLine();

                            switch (opGestion) {
                                case "1":
                                    listarDiccionario(service);
                                    break;
                                case "2":
                                    insertarElemento(service, sc);
                                    break;
                                case "3":
                                    modificarElemento(service, sc);
                                    break;
                                case "4":
                                    eliminarElemento(service, sc);
                                    break;
                                case "5":
                                    System.out.print("¿Estás seguro de que quieres borrar todo el diccionario? (s/n): ");
                                    String confirmacion = sc.nextLine();
                                    if (confirmacion.equalsIgnoreCase("s")) {
                                        service.vaciarDiccionario();
                                        System.out.println("Diccionario vaciado correctamente.");
                                    } else {
                                        System.out.println("Se ha cancelado la operación.");
                                    }
                                    break;
                                case "0":
                                    salirGestion = true;
                                    break;
                                default:
                                    System.out.println("Opción no válida.");
                            }
                        }
                    } else {
                        System.out.println("Contraseña incorrecta.");
                    }
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

        // Al salir, guardamos el diccionario
        if (service.escribirFichero()) {
            System.out.println("Diccionario guardado correctamente.");
        } else {
            System.out.println("No se pudo guardar el diccionario.");
        }

        sc.close();
    }

    private static boolean verificarContra(Scanner sc) {
        System.out.print("Introduce la contraseña: ");
        String entrada = sc.nextLine();
        return entrada.equals(Constantes.PASSWORD);
    }

    private static void listarDiccionario(GestionElementos service) {
        List<Elemento> lista = service.listarElementos(true); // true = ascendente
        if (lista.isEmpty()) {
            System.out.println("No hay elementos en el diccionario.");
            return;
        }

        System.out.println("\n--- DICCIONARIO ORDENADO ---");
        for (Elemento el : lista) {
            System.out.println("ID: " + el.getId() + ", Palabra: " + el.getPalabra() + ", Categoría: " + el.getCategoria());
        }
    }

    private static void insertarElemento(GestionElementos service, Scanner sc) {
        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Palabra: ");
        String palabra = sc.nextLine();
        System.out.print("Categoría: ");
        String categoria = sc.nextLine();

        Elemento nuevo = new Elemento(id, palabra, categoria);
        if (service.insertarElemento(nuevo)) {
            System.out.println("Se ha insertado el elemento correctamente.");
        } else {
            System.out.println("ERROR: No se ha podido insertar el elemento.");
        }
    }

    private static void modificarElemento(GestionElementos service, Scanner sc) {
        System.out.print("Introduzca el ID del elemento: ");
        String id = sc.nextLine();
        System.out.print("Nueva palabra: ");
        String palabra = sc.nextLine();

        if (service.modificarElemento(id, palabra)) {
            System.out.println("El elemento ha sido modificado con éxito.");
        } else {
            System.out.println("ERROR: No existe ningún elemento con ese ID.");
        }
    }

    private static void eliminarElemento(GestionElementos service, Scanner sc) {
        System.out.print("Introduzca el ID del elemento: ");
        String id = sc.nextLine();

        if (service.eliminarElemento(id)) {
            System.out.println("El elemento ha sido eliminado con éxito.");
        } else {
            System.out.println("ERROR: No existe ningún elemento con ese ID.");
        }
    }

}
