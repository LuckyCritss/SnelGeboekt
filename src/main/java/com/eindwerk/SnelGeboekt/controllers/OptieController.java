package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.optie.Optie;
import com.eindwerk.SnelGeboekt.instellingen.optie.OptieService;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class OptieController {

    private OptieService optieService;
    private OrganisatieService organisatieService;

    @Autowired
    public void setOrganisatieService(OrganisatieService organisatieService) {
        this.organisatieService = organisatieService;
    }

    @Autowired
    public void setOptieService(OptieService optieService) {
        this.optieService = optieService;
    }

    @GetMapping("/instellingen/keuzemogelijkheden")
    public String keuzemogelijkhedenHandler(Principal principal, Model model) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            List<Optie> opties = optieService.getOptiesByOrganisation(organisatieService.getOrganisatieByEmail(principal.getName()));
            model.addAttribute("opties", opties);
            return "/templatesInstellingen/keuzemogelijkheden";
        }
        return "redirect:/instellingen";
    }

    @GetMapping("/instellingen/keuzemogelijkheden/add")
    public String add(Model model,Principal principal) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            model.addAttribute("optie", new Optie());
            return "/templatesInstellingen/addkeuzemogelijkheden";
        }
        return "redirect:/instellingen";
    }

    @PostMapping("/instellingen/keuzemogelijkheden/add")
    public String addForm(@Valid @ModelAttribute Optie optie, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "/templatesInstellingen/addkeuzemogelijkheden";
        }
        try {
            optie.setOrganisatie(organisatieService.getOrganisatieByEmail(principal.getName()));
            optieService.saveOrUpdate(optie);
        }catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("optie_unique")) {
                bindingResult.rejectValue("optie","optie-unique",e.getMessage());
            }
            return "/templatesInstellingen/addkeuzemogelijkheden";
        }
        return "redirect:/instellingen/keuzemogelijkheden";
    }

    @GetMapping("/instellingen/keuzemogelijkheden/edit/{id}")
    public String editadd(@PathVariable int id, Model model) {
        Optie optie = optieService.getById(id);
        if (optie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        model.addAttribute("optie", optie);
        return "/templatesInstellingen/keuzemogelijkheden";
    }



    @PostMapping("/instellingen/keuzemogelijkheden/edit/{id}")
    public String processForm(@Valid @ModelAttribute Optie optie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/fragmentsInstellingen/keuzemogelijkheden";
        }
        optieService.saveOrUpdate(optie);
        return "redirect:/instellingen/keuzemogelijkheden";
    }



    @GetMapping("/instellingen/keuzemogelijkheden/delete/{id}")
    public String delete(@PathVariable int id){
        Optie optie = optieService.getById(id);
        if (optie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        optieService.delete(id);
        return "redirect:/instellingen/keuzemogelijkheden";
    }
}
