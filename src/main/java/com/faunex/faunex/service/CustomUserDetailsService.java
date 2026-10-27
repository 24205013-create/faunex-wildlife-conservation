package com.faunex.faunex.service;

import com.faunex.faunex.model.User;
import com.faunex.faunex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(">>> DEBUG: Login attempt for username: '" + username + "'");
        
        if (username == null || username.trim().isEmpty()) {
            System.out.println(">>> DEBUG: Username is empty or null");
            throw new UsernameNotFoundException("Username is empty");
        }

        User user = userRepository.findByUsername(username.trim())
                .orElse(null);

        if (user == null) {
            System.out.println(">>> DEBUG: User NOT FOUND in database for username: '" + username + "'");
            throw new UsernameNotFoundException("User not found: " + username);
        }

        System.out.println(">>> DEBUG: User found! Username: " + user.getUsername() + ", Role: " + user.getRole());
        
        String role = user.getRole();
        if (role == null || role.isEmpty()) {
            System.out.println(">>> DEBUG: User role is NULL or EMPTY for user: " + username);
            role = "VISITOR"; // Fallback to Visitor if somehow missing
        }
        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
        );
    }
}