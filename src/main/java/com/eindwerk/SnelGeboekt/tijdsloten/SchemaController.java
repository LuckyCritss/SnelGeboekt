package com.eindwerk.SnelGeboekt.tijdsloten;


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
