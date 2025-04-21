package org.xandr.common;

public class LetraRepeException extends RuntimeException {
    public LetraRepeException(char letra) {
        super("Ya has introducido la letra '" + letra + "'.");
    }

    public LetraRepeException(String message) {
        super(message);
    }
}
