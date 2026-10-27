package com.faunex.faunex.controller;

import com.faunex.faunex.model.Habitat;
import com.faunex.faunex.model.VisitRecord;
import com.faunex.faunex.model.Visitor;
import com.faunex.faunex.repository.HabitatRepository;
import com.faunex.faunex.repository.VisitRecordRepository;
import com.faunex.faunex.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/visits")
public class VisitRecordController {

    @Autowired
    private VisitRecordRepository visitRecordRepository;
    
    @Autowired
    private VisitorRepository visitorRepository;
    
    @Autowired
    private HabitatRepository habitatRepository;

    @Autowired
    private com.faunex.faunex.service.UserService userService;

    @GetMapping
    public String listVisits(org.springframework.security.core.Authentication auth, Model model) {
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VISITOR"))) {
            com.faunex.faunex.model.User user = userService.findByUsername(auth.getName());
            if (user != null) {
                com.faunex.faunex.model.Visitor visitor = visitorRepository.findByUser(user).orElse(null);
                if (visitor != null) {
                    model.addAttribute("visits", visitRecordRepository.findByVisitor(visitor));
                    return "visits/list";
                }
            }
        }
        model.addAttribute("visits", visitRecordRepository.findAll());
        return "visits/list";
    }

    @GetMapping("/add")
    public String addVisitForm(org.springframework.security.core.Authentication auth, Model model) {
        VisitRecord visit = new VisitRecord();
        visit.setVisitDate(LocalDateTime.now());
        
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VISITOR"))) {
            com.faunex.faunex.model.User user = userService.findByUsername(auth.getName());
            if (user != null) {
                com.faunex.faunex.model.Visitor visitor = visitorRepository.findByUser(user).orElse(null);
                visit.setVisitor(visitor);
                model.addAttribute("isVisitorUser", true);
            }
        } else {
            visit.setVisitor(new Visitor());
            model.addAttribute("isVisitorUser", false);
            model.addAttribute("visitors", visitorRepository.findAll());
        }

        visit.setHabitat(new Habitat());
        model.addAttribute("visit", visit);
        model.addAttribute("habitats", habitatRepository.findAll());
        return "visits/form";
    }

    @PostMapping("/save")
    public String saveVisit(@ModelAttribute VisitRecord visit, org.springframework.validation.BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/visits/add?error";
        }
        
        // Handle empty associations
        Visitor visitor = visit.getVisitor();
        if (visitor != null && visitor.getVisitorId() == null) {
            visit.setVisitor(null);
        }
        Habitat habitat = visit.getHabitat();
        if (habitat != null && habitat.getHabitatId() == null) {
            visit.setHabitat(null);
        }
        
        try {
            visitRecordRepository.save(visit);
        } catch (Exception e) {
            System.err.println("Error saving visit: " + e.getMessage());
            return "redirect:/visits/add?error";
        }
        return "redirect:/visits";
    }

    @GetMapping("/delete/{id}")
    public String deleteVisit(@PathVariable Long id) {
        visitRecordRepository.deleteById(id);
        return "redirect:/visits";
    }
}
