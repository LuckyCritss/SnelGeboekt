package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.MedewerkerService;
import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class MedewerkerController {

    private MedewerkerService medewerkerService;
    private OrganisatieService organisatieService;

    @Autowired
    public void setOrganisatieService(OrganisatieService organisatieService) {
        this.organisatieService = organisatieService;
    }

    @Autowired
    public void setMedewerkerService(MedewerkerService medewerkerService) {
        this.medewerkerService = medewerkerService;
    }


    @GetMapping("/instellingen/")
    public String list(Model model) {
        model.addAttribute("medewerkers", medewerkerService.getAll());
        return "fragmentsInstellingen/lijstmedewerkers";
    }

    @GetMapping("/instellingen/medewerker")
    public String medewerkerHandler(Principal principal, Model model) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            List<Medewerker> medewerkers = medewerkerService.getMedewerkersByOrganisation(organisatieService.getOrganisatieByEmail(principal.getName()));
            model.addAttribute("medewerkers", medewerkers);
            return "/templatesInstellingen/medewerker";
        }
        return "redirect:/instellingen";
    }

    @GetMapping("/instellingen/medewerker/add")
    public String add(Model model,Principal principal) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            model.addAttribute("medewerker", new Medewerker());
            return "/templatesInstellingen/addmedewerker";
        }
        return "redirect:/instellingen";
    }

    @PostMapping("/instellingen/medewerker/add")
    public String addForm(@Valid @ModelAttribute Medewerker medewerker, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "/templatesInstellingen/addmedewerker";
        }
        try {
            medewerker.setOrganisatie(organisatieService.getOrganisatieByEmail(principal.getName()));
            medewerkerService.saveOrUpdate(medewerker);
        }catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("medewerker_unique")) {
                bindingResult.rejectValue("medewerker","medewerker-unique",e.getMessage());
            }
            return "/templatesInstellingen/addmedewerker";
        }
        return "redirect:/instellingen/medewerker";
    }

    @GetMapping("/instellingen/medewerker/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Medewerker medewerker = medewerkerService.getById(id);
        if (medewerker == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        model.addAttribute("idMedewerker", id);
        model.addAttribute("medewerker", medewerker);
        return "/templatesInstellingen/addmedewerker";
    }
    
    @PostMapping("/instellingen/medewerker/edit/{id}")
    public String processForm(@PathVariable int id, @Valid @ModelAttribute Medewerker medewerker, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/fragmentsInstellingen/medewerker";
        }
        medewerkerService.saveOrUpdate(medewerker);
        return "redirect:/instellingen/medewerker";
    }

    @GetMapping("/instellingen/medewerker/delete/{id}")
    public String delete(@PathVariable int id){
        Medewerker medewerker = medewerkerService.getById(id);
        if (medewerker == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        medewerkerService.delete(id);
        return "redirect:/instellingen/medewerker";
    }
}
