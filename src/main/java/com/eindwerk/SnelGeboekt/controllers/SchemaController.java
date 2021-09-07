package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.TijdslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schema")
public class SchemaController {

    private TijdslotRepository tijdslotRepository;

    @Autowired
    public void setTijdslotRepository(TijdslotRepository tijdslotRepository) {
        this.tijdslotRepository = tijdslotRepository;
    }

    @GetMapping("/tijdslot")
    public String timeTange(Model model) {
        model.addAttribute("tijdsloten", tijdslotRepository.findAll());
        return "tijdslot";
    }
}
