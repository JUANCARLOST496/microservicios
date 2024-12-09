package es.uniovi.miw.ws.appfinanzas.finanzasaplication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        // Verificar si el encabezado Authorization contiene un token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Extraer el token
        String token = authHeader.replace("Bearer ", "");

        // Log del token recibido
        System.out.println("Token recibido: " + token);

        try {
            // Validar el token
            Claims claims = JwtUtil.getClaimsFromToken(token);
            System.out.println("Claims: " + claims);

            // Configurar la autenticación en el contexto de Spring
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            claims.getSubject(), // Nombre de usuario (sub)
                            null, // Credenciales (no necesarias aquí)
                            Collections.singletonList(new SimpleGrantedAuthority("USER")) // Roles o permisos
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("Autenticación configurada para usuario: " + claims.getSubject());

            // Guardar los claims en la solicitud si son necesarios
            request.setAttribute("claims", claims);
        } catch (JwtException e) {
            // Si el token no es válido, devolvemos 401
            System.out.println("Token inválido o expirado: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido o expirado");
            return;
        }

        // Continuar con la solicitud
        System.out.println("Pasando al siguiente filtro o controlador.");
        chain.doFilter(request, response);
    }
}
