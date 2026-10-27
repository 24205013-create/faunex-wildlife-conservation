package com.faunex.faunex.controller;

import com.faunex.faunex.model.Visitor;
import com.faunex.faunex.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/visitors")
public class VisitorController {

    @Autowired
    private VisitorRepository visitorRepository;

    @GetMapping
    public String listVisitors(Model model) {
        model.addAttribute("visitors", visitorRepository.findAll());
        return "visitors/list";
    }

    @GetMapping("/add")
    public String addVisitorForm(Model model) {
        model.addAttribute("visitor", new Visitor());
        return "visitors/form";
    }

    @PostMapping("/save")
    public String saveVisitor(@ModelAttribute Visitor visitor) {
        visitorRepository.save(visitor);
        return "redirect:/visitors";
    }

    @GetMapping("/edit/{id}")
    public String editVisitorForm(@PathVariable Long id, Model model) {
        Visitor visitor = visitorRepository.findById(id).orElseThrow();
        model.addAttribute("visitor", visitor);
        return "visitors/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteVisitor(@PathVariable Long id) {
        visitorRepository.deleteById(id);
        return "redirect:/visitors";
    }
}
