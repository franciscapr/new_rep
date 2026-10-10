package com.duoc.backend.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserDataInitializer {

    @Bean
    CommandLineRunner initUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository.count() == 0) {

                User user1 = new User();
                user1.setUsername("admin");
                user1.setEmail("admin@duoc.cl");
                user1.setPassword(passwordEncoder.encode("Admin123"));
                user1.setEnabled(true);

                User user2 = new User();
                user2.setUsername("usuario1");
                user2.setEmail("usuario1@duoc.cl");
                user2.setPassword(passwordEncoder.encode("Usuario123"));
                user2.setEnabled(true);

                User user3 = new User();
                user3.setUsername("usuario2");
                user3.setEmail("usuario2@duoc.cl");
                user3.setPassword(passwordEncoder.encode("Usuario456"));
                user3.setEnabled(true);

                userRepository.save(user1);
                userRepository.save(user2);
                userRepository.save(user3);

                System.out.println("Usuarios iniciales creados correctamente.");
            }
        };
    }
}