package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.optie.OptieService;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.Tijdsloten;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.TijdslotenService;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

import java.security.Principal;
import java.util.List;

@Controller
public class TijdslotController {

    private OrganisatieService organisatieService;
    private TijdslotenService tijdslotenService;
    private OptieService optieService;

    @Autowired
    public void setOrganisatieService(OrganisatieService organisatieService){
        this.organisatieService = organisatieService;
    }

    @Autowired
    public void setTijdslotenService(TijdslotenService tijdslotenService) {
        this.tijdslotenService = tijdslotenService;
    }

    @Autowired
    public void setOptieService(OptieService optieService) {
        this.optieService = optieService;
    }

    @GetMapping("/instellingen/tijdslot")
    public String tijdslotHandler(Principal principal, Model model) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            List<Tijdsloten> tijdslot = tijdslotenService.getAll();
            model.addAttribute("tijdlot", new Tijdsloten());
            return ("/templatesInstellingen/tijdslot");
        }
        return "redirect:/instellingen";
    }

    @GetMapping("/instellingen/tijdslot/add")
    public String add(Model model,Principal principal) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            model.addAttribute("tijdsloten", new Tijdsloten());
            return "/templatesInstellingen/addtijdsloten";
        }
        return "redirect:/instellingen";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
