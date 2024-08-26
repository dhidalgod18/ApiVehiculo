package com.gcatechnologies.alquilervehiculos.security.validar;

public class JwtAutenticarException extends RuntimeException {
    public JwtAutenticarException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtAutenticarException(String message) {
        super(message);
    }

}
