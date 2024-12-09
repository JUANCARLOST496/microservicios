package es.uniovi.miw.ws.appfinanzas.finanzasaplication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para pruebas con Postman
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll() // Rutas públicas
                        .anyRequest().authenticated() // Rutas protegidas
                )
                .addFilterBefore(new JwtFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class); // Registrar el filtro
        return http.build();
    }
}

