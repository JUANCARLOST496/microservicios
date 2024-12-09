package es.uniovi.miw.ws.appfinanzas.finanzasaplication.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // Generar una clave segura usando Keys.hmacShaKeyFor
    private static final String SECRET_KEY = "my-super-secret-key-for-jwt-generation-12345678";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    private static final long EXPIRATION_TIME = 86400000; // 1 día en milisegundos

    // Generar un token JWT para un usuario
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Información del usuario (sub)
                .setIssuedAt(new Date()) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Fecha de expiración
                .signWith(KEY, SignatureAlgorithm.HS256) // Usamos la clave y el algoritmo HMAC
                .compact();
    }

    // Validar el token JWT
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true; // Si no hay excepciones, el token es válido
        } catch (Exception e) {
            return false; // Si hay una excepción, el token no es válido
        }
    }

    // Obtener claims del token JWT
    public static Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
