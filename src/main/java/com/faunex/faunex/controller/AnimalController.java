package com.faunex.faunex.controller;

import com.faunex.faunex.model.Animal;
import com.faunex.faunex.model.Habitat;
import com.faunex.faunex.model.Species;
import com.faunex.faunex.repository.AnimalRepository;
import com.faunex.faunex.repository.HabitatRepository;
import com.faunex.faunex.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;
    
    @Autowired
    private SpeciesRepository speciesRepository;
    
    @Autowired
    private HabitatRepository habitatRepository;

    @GetMapping
    public String listAnimals(Model model) {
        model.addAttribute("animals", animalRepository.findAll());
        return "animals/list";
    }

    @GetMapping("/add")
    public String addAnimalForm(Model model) {
        Animal animal = new Animal();
        animal.setSpecies(new Species());
        animal.setHabitat(new Habitat());
        model.addAttribute("animal", animal);
        model.addAttribute("speciesList", speciesRepository.findAll());
        model.addAttribute("habitats", habitatRepository.findAll());
        return "animals/form";
    }

    @PostMapping("/save")
    public String saveAnimal(@ModelAttribute Animal animal, org.springframework.validation.BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/animals/add?error";
        }
        
        // Handle empty associations
        Species species = animal.getSpecies();
        if (species != null && species.getSpeciesId() == null) {
            animal.setSpecies(null);
        }
        Habitat habitat = animal.getHabitat();
        if (habitat != null && habitat.getHabitatId() == null) {
            animal.setHabitat(null);
        }
        
        try {
            animalRepository.save(animal);
        } catch (Exception e) {
            System.err.println("Error saving animal: " + e.getMessage());
            return "redirect:/animals/add?error";
        }
        return "redirect:/animals";
    }

    @GetMapping("/edit/{id}")
    public String editAnimalForm(@PathVariable Long id, Model model) {
        Animal animal = animalRepository.findById(id).orElseThrow();
        if (animal.getSpecies() == null) animal.setSpecies(new Species());
        if (animal.getHabitat() == null) animal.setHabitat(new Habitat());
        model.addAttribute("animal", animal);
        model.addAttribute("speciesList", speciesRepository.findAll());
        model.addAttribute("habitats", habitatRepository.findAll());
        return "animals/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteAnimal(@PathVariable Long id) {
        animalRepository.deleteById(id);
        return "redirect:/animals";
    }
}