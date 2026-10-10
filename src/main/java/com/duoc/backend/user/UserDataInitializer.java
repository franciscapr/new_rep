
package com.duoc.backend.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserDataInitializer {

    @Bean
    @ConditionalOnProperty(
        name = "app.bootstrap.enabled",
        havingValue = "true"
    )
    CommandLineRunner initUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // Obtener contraseñas desde variables de entorno
            String adminPassword =
                    obtenerVariable("BOOTSTRAP_ADMIN_PASSWORD");

            String usuario1Password =
                    obtenerVariable("BOOTSTRAP_USUARIO1_PASSWORD");

            String usuario2Password =
                    obtenerVariable("BOOTSTRAP_USUARIO2_PASSWORD");

            // Crear o actualizar usuarios iniciales
            guardarUsuario(
                userRepository,
                passwordEncoder,
                "admin",
                "admin@duoc.cl",
                adminPassword
            );

            guardarUsuario(
                userRepository,
                passwordEncoder,
                "usuario1",
                "usuario1@duoc.cl",
                usuario1Password
            );

            guardarUsuario(
                userRepository,
                passwordEncoder,
                "usuario2",
                "usuario2@duoc.cl",
                usuario2Password
            );
        };
    }

    private static String obtenerVariable(String nombre) {

        String valor = System.getenv(nombre);

        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException(
                "Variable de entorno no configurada: " + nombre
            );
        }

        if (valor.length() < 12) {
            throw new IllegalStateException(
                "La contraseña debe tener al menos 12 caracteres: "
                + nombre
            );
        }

        return valor;
    }

    private static void guardarUsuario(
            UserRepository repository,
            PasswordEncoder encoder,
            String username,
            String email,
            String password) {

        User usuario = repository.findByUsername(username);

        if (usuario == null) {
            usuario = new User();
            usuario.setUsername(username);
            usuario.setEmail(email);
            usuario.setEnabled(true);
        }

        // Guardar únicamente el hash BCrypt
        usuario.setPassword(encoder.encode(password));

        repository.save(usuario);
    }
}
