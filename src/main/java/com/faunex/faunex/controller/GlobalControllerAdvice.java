package com.faunex.faunex.controller;

import com.faunex.faunex.model.User;
import com.faunex.faunex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserService userService;

    @ModelAttribute("currentUser")
    public User addUserToModel(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() && 
            authentication.getName() != null && !authentication.getName().equals("anonymousUser")) {
            User user = userService.findByUsername(authentication.getName());
            if (user == null) {
                // Fallback for missing database user
                User fallback = new User();
                fallback.setUsername(authentication.getName());
                fallback.setRole("UNKNOWN");
                return fallback;
            }
            return user;
        }
        return null;
    }
}
