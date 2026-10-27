package com.faunex.faunex.controller;

import com.faunex.faunex.model.Species;
import com.faunex.faunex.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/species")
public class SpeciesController {

    @Autowired
    private SpeciesRepository speciesRepository;

    @GetMapping
    public String listSpecies(Model model) {
        model.addAttribute("speciesList", speciesRepository.findAll());
        return "species/list";
    }

    @GetMapping("/add")
    public String addSpeciesForm(Model model) {
        model.addAttribute("species", new Species());
        return "species/form";
    }

    @PostMapping("/save")
    public String saveSpecies(@ModelAttribute Species species) {
        speciesRepository.save(species);
        return "redirect:/species";
    }

    @GetMapping("/edit/{id}")
    public String editSpeciesForm(@PathVariable Long id, Model model) {
        Species species = speciesRepository.findById(id).orElseThrow();
        model.addAttribute("species", species);
        return "species/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteSpecies(@PathVariable Long id) {
        speciesRepository.deleteById(id);
        return "redirect:/species";
    }
}
