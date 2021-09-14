package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.MedewerkerService;
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

    @Autowired
    public void setOptieService(OptieService optieService) {
        this.optieService = optieService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("opties", optieService.getAll());
        return "fragmentsInstellingen/lijstkeuzemogelijk";
    }

    @GetMapping("/instellingen/medewerker/{id}/keuzemogelijkheden")
    public String keuzemogelijkhedenHandler(@PathVariable int id, Principal principal, Model model) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            List<Optie> opties = optieService.getOptiesByMedewerker(medewerkerService.getMedewerkerById(id));
            model.addAttribute("idMedewerker", id);
            model.addAttribute("opties", opties);
            return "/templatesInstellingen/keuzemogelijkheden";
        }
        return "redirect:/instellingen";
    }

    @GetMapping("/instellingen/medewerker/{id}/keuzemogelijkheden/add")
    public String add(@PathVariable int id, Model model,Principal principal) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            model.addAttribute("idMedewerker", id);
            model.addAttribute("optie", new Optie());
            return "/templatesInstellingen/addkeuzemogelijkheden";
        }
        return "redirect:/instellingen";
    }

    @PostMapping("/instellingen/medewerker/{id}/keuzemogelijkheden/add")
    public String addForm(@PathVariable int id, @Valid @ModelAttribute Optie optie, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "/templatesInstellingen/addkeuzemogelijkheden";
        }
        try {
            optie.setMedewerker(medewerkerService.getMedewerkerById(id));
            optieService.saveOrUpdate(optie);
        }catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("optie_unique")) {
                bindingResult.rejectValue("optie","optie-unique",e.getMessage());
            }
            return "/templatesInstellingen/addkeuzemogelijkheden";
        }
        return "redirect:/instellingen/keuzemogelijkheden";
    }

    @GetMapping("/instellingen/medewerker/{id}/keuzemogelijkheden/edit/{idOptie}")
    public String edit(@PathVariable int id, @PathVariable int idOptie, Model model) {
        Optie optie = optieService.getById(idOptie);
        if (optie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        model.addAttribute("idMedewerker", id);
        model.addAttribute("idOptie", idOptie);
        model.addAttribute("optie", optie);
        return "/templatesInstellingen/addkeuzemogelijkheden";
    }
    
    @PostMapping("/instellingen/medewerker/{id}/keuzemogelijkheden/edit/{idOptie}")
    public String processForm(@PathVariable int id, @PathVariable int idOptie, @Valid @ModelAttribute Optie optie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/fragmentsInstellingen/keuzemogelijkheden";
        }
        optieService.saveOrUpdate(optie);
        return "redirect:/instellingen/keuzemogelijkheden";
    }

    @GetMapping("/instellingen/medewerker/{id}/keuzemogelijkheden/delete/{idOptie}")
    public String delete(@PathVariable int id, @PathVariable int idOptie){
        Optie optie = optieService.getById(idOptie);
        if (optie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        optieService.delete(idOptie);
        return "redirect:/instellingen/keuzemogelijkheden";
    }
}
