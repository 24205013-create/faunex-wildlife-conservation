package com.faunex.faunex.controller;

import com.faunex.faunex.model.Habitat;
import com.faunex.faunex.repository.HabitatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/habitats")
public class HabitatController {

    @Autowired
    private HabitatRepository habitatRepository;

    @GetMapping
    public String listHabitats(Model model) {
        model.addAttribute("habitats", habitatRepository.findAll());
        return "habitats/list";
    }

    @GetMapping("/add")
    public String addHabitatForm(Model model) {
        model.addAttribute("habitat", new Habitat());
        return "habitats/form";
    }

    @PostMapping("/save")
    public String saveHabitat(@ModelAttribute Habitat habitat) {
        habitatRepository.save(habitat);
        return "redirect:/habitats";
    }

    @GetMapping("/edit/{id}")
    public String editHabitatForm(@PathVariable Long id, Model model) {
        Habitat habitat = habitatRepository.findById(id).orElseThrow();
        model.addAttribute("habitat", habitat);
        return "habitats/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteHabitat(@PathVariable Long id) {
        habitatRepository.deleteById(id);
        return "redirect:/habitats";
    }
}
