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
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para evitar problemas en pruebas con clientes
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll() // Permitir acceso público al endpoint de login
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll() // Permitir todas las solicitudes OPTIONS para CORS
                        .requestMatchers("/gastos/**").authenticated() // Proteger las rutas de gastos
                        .requestMatchers("/some-public-endpoint/**").permitAll() // Hacer público un endpoint específico
                        .anyRequest().authenticated() // Proteger cualquier otra solicitud
                )
                .addFilterBefore(new JwtFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class); // Registrar el filtro JWT para validar tokens
        return http.build();
    }
}
