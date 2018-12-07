package de.tub.ise.ec.exceptions;

public class KeyStoreException extends Exception {

    public KeyStoreException() {

    }

    public KeyStoreException(String message) {
        super(message);
    }
    public KeyStoreException(String message, Throwable cause) {
        super(message, cause);
    }

}
