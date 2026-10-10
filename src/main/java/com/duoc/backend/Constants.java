
package com.duoc.backend;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public final class Constants {

    private Constants() {
        // Clase de constantes: no debe instanciarse.
    }

    // Spring Security
    public static final String LOGIN_URL = "/login";
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    // JWT
    public static final String ISSUER_INFO =
            "https://www.duocuc.cl/";

    public static final String SUPER_SECRET_KEY =
            cargarClaveJwt();

    public static final long TOKEN_EXPIRATION_TIME =
            86_400_000L; // 1 día en milisegundos

    // Obtener la clave desde variables de entorno
    private static String cargarClaveJwt() {

        String secret = System.getenv("JWT_SECRET");

        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException(
                "La variable de entorno JWT_SECRET no esta configurada"
            );
        }

        return secret.trim();
    }

    // Decodificar clave Base64 para firmar JWT
    public static Key getSigningKeyB64(String secret) {

        byte[] keyBytes = Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Usar la misma clave para validar JWT
    public static Key getSigningKey(String secret) {

        return getSigningKeyB64(secret);
    }
}
