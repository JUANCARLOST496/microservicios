package es.uniovi.miw.ws.appfinanzas.finanzasaplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println("Iniciando la aplicación...");
        SpringApplication.run(Application.class, args);
        System.out.println("Aplicación iniciada correctamente.");
    }
}
