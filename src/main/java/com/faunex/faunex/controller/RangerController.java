package com.faunex.faunex.controller;

import com.faunex.faunex.model.Ranger;
import com.faunex.faunex.repository.RangerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/rangers")
public class RangerController {

    @Autowired
    private RangerRepository rangerRepository;

    @GetMapping
    public String listRangers(Model model) {
        model.addAttribute("rangers", rangerRepository.findAll());
        return "rangers/list";
    }

    @GetMapping("/add")
    public String addRangerForm(Model model) {
        Ranger ranger = new Ranger();
        ranger.setJoinDate(LocalDate.now());
        model.addAttribute("ranger", ranger);
        return "rangers/form";
    }

    @PostMapping("/save")
    public String saveRanger(@ModelAttribute Ranger ranger, org.springframework.validation.BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/rangers/add?error";
        }
        try {
            rangerRepository.save(ranger);
        } catch (Exception e) {
            System.err.println("Error saving ranger: " + e.getMessage());
            return "redirect:/rangers/add?error";
        }
        return "redirect:/rangers";
    }

    @GetMapping("/edit/{id}")
    public String editRangerForm(@PathVariable Long id, Model model) {
        Ranger ranger = rangerRepository.findById(id).orElseThrow();
        model.addAttribute("ranger", ranger);
        return "rangers/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteRanger(@PathVariable Long id) {
        rangerRepository.deleteById(id);
        return "redirect:/rangers";
    }
}
