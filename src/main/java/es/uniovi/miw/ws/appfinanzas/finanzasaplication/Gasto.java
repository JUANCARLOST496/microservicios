package es.uniovi.miw.ws.appfinanzas.finanzasaplication;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class Gasto {
    @GetMapping("/hello") // Define el endpoint para solicitudes GET
    public String sayHello() {
        return "Hello, World!";
    }
}
