package org.xandr.common;

public class CategoriaInvException extends RuntimeException {
    public CategoriaInvException() {
        super("La categoría introducida no existe.");
    }

    public CategoriaInvException(String message) {
        super(message);
    }
}
