package com.gcatechnologies.alquilervehiculos.security.auth;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class TokenJwtConfig {
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTORIZATION = "Authorization";

    // Constructor privado para evitar la instanciación de la clase
    private TokenJwtConfig() {
        throw new IllegalStateException("Utility class");
    }
}
