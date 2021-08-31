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
import javax.validation.constraints.Email;
import java.security.Principal;
import java.util.ArrayList;
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
            return "keuzemogelijkheden";
        }
        return "redirect:/instellingen";
    }

    @GetMapping("/instellingen/keuzemogelijkheden/add")
    public String add(Model model,Principal principal) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            model.addAttribute("optie", new Optie());
            return "addkeuzemogelijkheden";
        }
        return "redirect:/instellingen";
    }

    @PostMapping("/instellingen/keuzemogelijkheden/add")
    public String addForm(@Valid @ModelAttribute Optie optie, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "addkeuzemogelijkheden";
        }
        try {
            Organisatie organisatie = organisatieService.getOrganisatieByEmail(principal.getName());
            optie.setOrganisatie(organisatie);
            optieService.saveOrUpdate(optie);
        }catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("optie_unique")) {
                bindingResult.rejectValue("optie","optie-unique",e.getMessage());
            }
            return "addkeuzemogelijkheden";
        }
        return "redirect:/instellingen/keuzemogelijkheden";
    }

    @PostMapping("/instellingen/keuzemogelijkheden/edit")
    public String processForm(@Valid @ModelAttribute Optie optie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "keuzemogelijheden";
        }
        optieService.saveOrUpdate(optie);
        return "redirect:/keuzemogelijheden";
    }

    @GetMapping("/instellingen/keuzemogelijkheden/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Optie optie = optieService.getById(id);
        if (optie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        model.addAttribute("optie", optie);
        return "fragments/keuzemogelijkheden";
    }

    @GetMapping("/instellingen/keuzemogelijkheden/delete/{id}")
    public String delete(@PathVariable int id){
        Optie optie = optieService.getById(id);
        if (optie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        optieService.delete(id);
        return "redirect:/keuzemogelijkheden";
    }
}
