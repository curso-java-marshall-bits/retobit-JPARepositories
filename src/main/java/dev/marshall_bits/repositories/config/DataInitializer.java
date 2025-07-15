package dev.marshall_bits.repositories.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        System.out.println("=== RetoBit JPA ===");
        System.out.println("Entidades listas:");
        System.out.println("- User: representa un sistema de usuarios con posts");
        System.out.println("- Post: Sistema de publicaciones (posts) con un autor y categoría enum");
        System.out.println("Te toca a ti implementar los repositorios y servicios para estas entidades 😉");
    }
}
