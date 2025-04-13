package es.uniovi.miw.ws.appfinanzas.finanzasaplication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtException;

import java.security.Key;


public class JwtUtil {

    // La misma clave secreta usada en el servicio de login
    private static final String SECRET_KEY = "my-super-secret-key-for-jwt-generation-12345678";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Método para validar el token
    public static Claims getClaimsFromToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
