package com.faunex.faunex.controller;

import com.faunex.faunex.model.User;
import com.faunex.faunex.model.Ranger;
import com.faunex.faunex.repository.*;
import com.faunex.faunex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private SpeciesRepository speciesRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private HabitatRepository habitatRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private VisitRecordRepository visitRecordRepository;

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private RangerRepository rangerRepository;

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }
        
        try {
            // Add real counts to the model
            model.addAttribute("speciesCount", speciesRepository.count());
            model.addAttribute("animalCount", animalRepository.count());
            model.addAttribute("habitatCount", habitatRepository.count());
            model.addAttribute("visitCount", visitRecordRepository.count());
            
            // Add recent species
            model.addAttribute("recentSpecies", speciesRepository.findAll(PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "speciesId"))).getContent());
            
            // Add role-specific data
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RANGER"))) {
                User user = userService.findByUsername(authentication.getName());
                if (user != null) {
                    Ranger ranger = rangerRepository.findByUser(user).orElse(null);
                    if (ranger != null) {
                        model.addAttribute("openIncidents", incidentRepository.findByRanger(ranger));
                    } else {
                        model.addAttribute("openIncidents", java.util.Collections.emptyList());
                    }
                } else {
                    model.addAttribute("openIncidents", java.util.Collections.emptyList());
                }
            } else {
                model.addAttribute("openIncidents", incidentRepository.findAll());
            }

            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VISITOR"))) {
                User user = userService.findByUsername(authentication.getName());
                if (user != null && user.getVisitor() != null) {
                    model.addAttribute("recentVisits", visitRecordRepository.findByVisitor(user.getVisitor()));
                } else {
                    model.addAttribute("recentVisits", java.util.Collections.emptyList());
                }
            } else {
                model.addAttribute("recentVisits", visitRecordRepository.findAll(PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "visitId"))).getContent());
            }
        } catch (Exception e) {
            System.err.println("Dashboard error: " + e.getMessage());
            model.addAttribute("speciesCount", 0);
            model.addAttribute("animalCount", 0);
            model.addAttribute("habitatCount", 0);
            model.addAttribute("visitCount", 0);
            model.addAttribute("recentSpecies", java.util.Collections.emptyList());
            model.addAttribute("openIncidents", java.util.Collections.emptyList());
            model.addAttribute("recentVisits", java.util.Collections.emptyList());
        }
        
        return "dashboard";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }
}
