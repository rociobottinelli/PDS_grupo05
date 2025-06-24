package com.pds.partidosapp.shared.exceptions;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String mensaje) {
        super(mensaje);
    }
}
