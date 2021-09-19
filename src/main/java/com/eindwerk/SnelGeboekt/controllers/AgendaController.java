package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.TijdslotRepository;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

import java.security.Principal;

@Controller
public class AgendaController {

    private OrganisatieService organisatieService;

    private final TijdslotRepository tijdslotRepository;

    public AgendaController(TijdslotRepository tijdslotRepository){
        this.tijdslotRepository = tijdslotRepository;
    }


    @Autowired
    public void setOrganisatieService(OrganisatieService organisatieService){
        this.organisatieService = organisatieService;
    }

    @GetMapping("/instellingen/agenda")
    public String agendaHandler(Principal principal, Model model) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            Organisatie organisatie = organisatieService.getOrganisatieByEmail(principal.getName());
            model.addAttribute("organisatie", organisatie);
            return ("/templatesInstellingen/kalender");
        }
        return "redirect:/instellingen";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
