package com.eindwerk.SnelGeboekt.controllers;


import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.TijdslotenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schema")
public class SchemaController {

    private final TijdslotenRepository tijdslotenRepository;

    public SchemaController(TijdslotenRepository tijdslotenRepository) {
        this.tijdslotenRepository = tijdslotenRepository;
    }

    @GetMapping("/tijdslot")
    public String timeTange(Model model) {
        model.addAttribute("tijdsloten", tijdslotenRepository.findAll());
        return "tijdslot";
    }
}
