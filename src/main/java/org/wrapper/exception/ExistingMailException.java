package org.wrapper.exception;

public class ExistingMailException extends RuntimeException {
    public ExistingMailException(String mensaje) {
        super(mensaje);
    }
}
