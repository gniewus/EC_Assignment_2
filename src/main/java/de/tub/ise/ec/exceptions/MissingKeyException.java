package de.tub.ise.ec.exceptions;

public class MissingKeyException extends Exception {

    public MissingKeyException() {

    }

    public MissingKeyException(String message) {
        super(message);
    }
    public MissingKeyException(String message, Throwable cause) {
        super(message, cause);
    }

}
