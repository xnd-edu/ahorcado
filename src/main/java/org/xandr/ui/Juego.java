package org.xandr.ui;

import org.xandr.domain.Elemento;
import org.xandr.common.LetraRepeException;
import org.xandr.common.LetraInvException;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Juego {
    private final Elemento palabra;
    private final Set<Character> letrasUsadas = new HashSet<>();
    private final Set<Character> letrasCorrectas = new HashSet<>();
    private int intentosRestantes = 6;

    public Juego(Elemento palabra) {
        this.palabra = palabra;
    }

    public void jugar(Scanner sc) {
        System.out.println("\nEmpezando un nuevo juego...");
        String solucion = palabra.getPalabra().toLowerCase();

        while (intentosRestantes > 0) {
            mostrarEstado(solucion);
            System.out.print("Introduce una letra o intenta resolver: ");
            String intento = sc.nextLine().toLowerCase();

            try {
                if (intento.length() == 1) {
                    char letra = intento.charAt(0);

                    if (!Character.isLetter(letra)) {
                        throw new LetraInvException();
                    }

                    if (letrasUsadas.contains(letra)) {
                        throw new LetraRepeException(letra);
                    }

                    letrasUsadas.add(letra);

                    if (solucion.indexOf(letra) >= 0) {
                        letrasCorrectas.add(letra);
                        System.out.println("¡BIEN!");
                    } else {
                        intentosRestantes--;
                        System.out.println("FALLO. Intentos restantes: " + intentosRestantes);
                    }

                    if (palabraAdivinada(solucion)) {
                        System.out.println("¡HAS GANADO! La palabra era: " + palabra.getPalabra());
                        return;
                    }

                } else if (intento.length() == solucion.length()) {
                    if (intento.equals(solucion)) {
                        System.out.println("¡Has acertado la palabra entera!");
                        return;
                    } else {
                        intentosRestantes--;
                        System.out.println("INCORRECTO. Intentos restantes: " + intentosRestantes);
                    }
                } else {
                    System.out.println("ERROR: Introduce una sola letra o intenta resolver escribiendo toda la palabra.");
                }

            } catch (LetraInvException | LetraRepeException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("HAS PERDIDO. La palabra era: " + palabra.getPalabra());
    }

    private void mostrarEstado(String solucion) {
        StringBuilder estado = new StringBuilder();
        for (char c : solucion.toCharArray()) {
            if (letrasCorrectas.contains(c)) {
                estado.append(c).append(" ");
            } else {
                estado.append("_ ");
            }
        }
        System.out.println("\nPalabra: " + estado);
        System.out.println("Letras usadas: " + letrasUsadas);
    }

    private boolean palabraAdivinada(String solucion) {
        for (char c : solucion.toCharArray()) {
            if (!letrasCorrectas.contains(c)) return false;
        }
        return true;
    }
}
