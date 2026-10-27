package com.faunex.faunex.controller;

import com.faunex.faunex.model.Animal;
import com.faunex.faunex.model.Habitat;
import com.faunex.faunex.model.Incident;
import com.faunex.faunex.model.Ranger;
import com.faunex.faunex.repository.AnimalRepository;
import com.faunex.faunex.repository.HabitatRepository;
import com.faunex.faunex.repository.IncidentRepository;
import com.faunex.faunex.repository.RangerRepository;
import com.faunex.faunex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/incidents")
public class IncidentController {
    // Controller for managing incidents reported in the park

    @Autowired
    private IncidentRepository incidentRepository;
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @Autowired
    private RangerRepository rangerRepository;

    @Autowired
    private HabitatRepository habitatRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listIncidents(Model model) {
        model.addAttribute("incidents", incidentRepository.findAll());
        return "incidents/list";
    }

    @GetMapping("/add")
    public String addIncidentForm(org.springframework.security.core.Authentication auth, Model model) {
        System.out.println(">>> DEBUG: Entering addIncidentForm");
        try {
            Incident incident = new Incident();
            incident.setIncidentDate(LocalDateTime.now());
            incident.setIncidentStatus("Normal");
            
            // Explicitly initialize nested objects for the form to prevent null binding issues
            incident.setAnimal(new Animal());
            incident.setHabitat(new Habitat());
            
            if (auth != null) {
                String username = auth.getName();
                System.out.println(">>> DEBUG: Authenticated user: " + username);
                boolean isRanger = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RANGER"));
                
                if (isRanger) {
                    System.out.println(">>> DEBUG: User is a RANGER");
                    Ranger ranger = (Ranger) rangerRepository.findByUserUsername(username).orElse(null);
                    if (ranger != null) {
                        System.out.println(">>> DEBUG: Found Ranger profile for user: " + ranger.getName());
                        incident.setRanger(ranger);
                        model.addAttribute("isRangerUser", true);
                    } else {
                        System.out.println(">>> DEBUG: Ranger profile NOT FOUND for user: " + username);
                        incident.setRanger(new Ranger());
                        model.addAttribute("isRangerUser", false);
                        model.addAttribute("rangers", rangerRepository.findAll());
                    }
                } else {
                    System.out.println(">>> DEBUG: User is NOT a RANGER");
                    incident.setRanger(new Ranger());
                    model.addAttribute("isRangerUser", false);
                    model.addAttribute("rangers", rangerRepository.findAll());
                }
            } else {
                System.out.println(">>> DEBUG: User NOT authenticated");
                incident.setRanger(new Ranger());
                model.addAttribute("isRangerUser", false);
                model.addAttribute("rangers", rangerRepository.findAll());
            }
            
            model.addAttribute("incident", incident);
            model.addAttribute("animals", animalRepository.findAll());
            model.addAttribute("habitats", habitatRepository.findAll());
            
            System.out.println(">>> DEBUG: Returning incidents/form");
            return "incidents/form";
        } catch (Exception e) {
            java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
            java.io.PrintWriter pw = new java.io.PrintWriter(cw);
            e.printStackTrace(pw);
            System.err.println(">>> DEBUG STACKTRACE: " + cw.toString());
            model.addAttribute("message", "Error initializing form: " + e.getMessage());
            model.addAttribute("exception", cw.toString());
            return "error";
        }
    }

    @PostMapping("/save")
    public String saveIncident(@ModelAttribute Incident incident, org.springframework.validation.BindingResult result, org.springframework.security.core.Authentication auth, Model model) {
        System.out.println(">>> DEBUG: Entering saveIncident");
        if (result.hasErrors()) {
            System.out.println(">>> DEBUG: Validation errors: " + result.getAllErrors());
            model.addAttribute("animals", animalRepository.findAll());
            model.addAttribute("habitats", habitatRepository.findAll());
            boolean isRanger = auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RANGER"));
            model.addAttribute("isRangerUser", isRanger);
            if (!isRanger) {
                model.addAttribute("rangers", rangerRepository.findAll());
            }
            return "incidents/form";
        }
        
        try {
            if (incident.getIncidentDate() == null) {
                incident.setIncidentDate(LocalDateTime.now());
            }
            
            // Ensure child objects are either fully populated or null to avoid foreign key issues with empty IDs
            Animal animal = incident.getAnimal();
            if (animal != null && animal.getAnimalId() == null) {
                incident.setAnimal(null);
            }
            
            // If the logged in user is a RANGER, force the ranger association to their profile
            if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RANGER"))) {
                System.out.println(">>> DEBUG: Forcing ranger association for ranger user: " + auth.getName());
                Ranger currentRanger = (Ranger) rangerRepository.findByUserUsername(auth.getName()).orElse(null);
                if (currentRanger != null) {
                    incident.setRanger(currentRanger);
                }
            } else {
                Ranger r = incident.getRanger();
                if (r != null && r.getRangerId() == null) {
                    incident.setRanger(null);
                }
            }
            
            Habitat h = incident.getHabitat();
            if (h != null && h.getHabitatId() == null) {
                incident.setHabitat(null);
            }
            
            System.out.println(">>> DEBUG: Saving incident type: " + incident.getIncidentType());
            incidentRepository.save(incident);
            System.out.println(">>> DEBUG: Incident saved successfully");
            return "redirect:/incidents";
        } catch (Exception e) {
            java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
            java.io.PrintWriter pw = new java.io.PrintWriter(cw);
            e.printStackTrace(pw);
            System.err.println(">>> DEBUG STACKTRACE (SAVE): " + cw.toString());
            model.addAttribute("message", "Database error: " + e.getMessage());
            model.addAttribute("exception", cw.toString());
            return "error";
        }
    }

    @GetMapping("/edit/{id}")
    public String editIncidentForm(org.springframework.security.core.Authentication auth, @PathVariable Long id, Model model) {
        Incident incident = incidentRepository.findById(id).orElseThrow();
        if (incident.getAnimal() == null) incident.setAnimal(new Animal());
        if (incident.getRanger() == null) incident.setRanger(new Ranger());
        if (incident.getHabitat() == null) incident.setHabitat(new Habitat());
        
        boolean isRanger = auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RANGER"));
        model.addAttribute("isRangerUser", isRanger);
        
        model.addAttribute("incident", incident);
        model.addAttribute("animals", animalRepository.findAll());
        model.addAttribute("rangers", rangerRepository.findAll());
        model.addAttribute("habitats", habitatRepository.findAll());
        return "incidents/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteIncident(@PathVariable Long id) {
        incidentRepository.deleteById(id);
        return "redirect:/incidents";
    }
}
