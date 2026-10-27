package com.faunex.faunex.config;

import com.faunex.faunex.model.User;
import com.faunex.faunex.service.UserService;
import com.faunex.faunex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            // Create Admin
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setRole("ADMIN");
            admin.setFullName("System Administrator");
            admin.setEmail("admin@faunex.com");
            userService.registerUser(admin);
            System.out.println("Default Admin account created: admin / admin123");
        }

        if (userRepository.findByUsername("ranger").isEmpty()) {
            // Create Ranger
            User ranger = new User();
            ranger.setUsername("ranger");
            ranger.setPassword("ranger123");
            ranger.setRole("RANGER");
            ranger.setFullName("Ranger John");
            ranger.setEmail("john@faunex.com");
            userService.registerUser(ranger);
            System.out.println("Default Ranger account created: ranger / ranger123");
        }

        if (userRepository.findByUsername("visitor").isEmpty()) {
            // Create Visitor
            User visitor = new User();
            visitor.setUsername("visitor");
            visitor.setPassword("visitor123");
            visitor.setRole("VISITOR");
            visitor.setFullName("Regular Visitor");
            visitor.setEmail("visitor@example.com");
            userService.registerUser(visitor);
            System.out.println("Default Visitor account created: visitor / visitor123");
        }
    }
}
