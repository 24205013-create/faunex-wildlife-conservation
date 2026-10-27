package com.faunex.faunex.service;

import com.faunex.faunex.model.User;
import com.faunex.faunex.model.Ranger;
import com.faunex.faunex.model.Visitor;
import com.faunex.faunex.repository.UserRepository;
import com.faunex.faunex.repository.RangerRepository;
import com.faunex.faunex.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RangerRepository rangerRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        
        if ("RANGER".equals(savedUser.getRole())) {
            Ranger ranger = new Ranger();
            ranger.setName(savedUser.getFullName());
            ranger.setUser(savedUser);
            ranger.setJoinDate(java.time.LocalDate.now());
            rangerRepository.save(ranger);
        } else if ("VISITOR".equals(savedUser.getRole())) {
            Visitor visitor = new Visitor();
            visitor.setName(savedUser.getFullName());
            visitor.setUser(savedUser);
            visitorRepository.save(visitor);
        }
        
        return savedUser;
    }

    public User findByUsername(String username) {
        if (username == null) return null;
        return userRepository.findByUsername(username.trim()).orElse(null);
    }
}
