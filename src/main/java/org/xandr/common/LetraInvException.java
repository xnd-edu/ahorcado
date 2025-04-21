package org.xandr.common;

public class LetraInvException extends RuntimeException {
    public LetraInvException() {
        super("Solo debes introducir una letra o la palabra completa si quieres resolver.");
    }

    public LetraInvException(String message) {
        super(message);
    }
}
