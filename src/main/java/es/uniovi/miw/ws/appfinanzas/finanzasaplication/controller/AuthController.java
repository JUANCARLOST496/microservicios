package es.uniovi.miw.ws.appfinanzas.finanzasaplication.controller;

import es.uniovi.miw.ws.appfinanzas.finanzasaplication.payload.LoginRequest;
import es.uniovi.miw.ws.appfinanzas.finanzasaplication.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // Usuario y contraseña predefinidos (variables temporales)
    private static final String DEFAULT_USER = "admin";
    private static final String DEFAULT_PASSWORD = "password";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Validar usuario y contraseña
        if (DEFAULT_USER.equals(loginRequest.getUsername()) &&
                DEFAULT_PASSWORD.equals(loginRequest.getPassword())) {
            // Generar token JWT
            String token = JwtUtil.generateToken(loginRequest.getUsername());
            return ResponseEntity.ok().body("{\"token\":\"" + token + "\"}");
        }
        return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
    }
}
