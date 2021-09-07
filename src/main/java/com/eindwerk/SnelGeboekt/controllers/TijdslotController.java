package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.optie.Optie;
import com.eindwerk.SnelGeboekt.instellingen.optie.OptieService;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.Tijdsloten;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.TijdslotenService;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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

    @PostMapping("/instellingen/tijdslot/add")
    public String addForm(@Valid @ModelAttribute Tijdsloten tijdsloten, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "/templatesInstellingen/addtijdsloten";
        }
        try {
            Organisatie organisatie = organisatieService.getOrganisatieByEmail(principal.getName());
            tijdsloten.setOrganisatie(organisatie);
            tijdslotenService.saveOrUpdate(tijdsloten);
        }catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("tijdsloten_unique")) {
                bindingResult.rejectValue("tijdsloten","tijdsloten-unique",e.getMessage());
            }
            return "/templatesInstellingen/addtijdsloten";
        }
        return "redirect:/instellingen/tijdslot";
    }

    @PostMapping("/instellingen/tijdslot/edit")
    public String processForm(@Valid @ModelAttribute Tijdsloten tijdsloten, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/templatesInstellingen/tijdslot";
        }
        tijdslotenService.saveOrUpdate(tijdsloten);
        return "redirect:/instellingen/tijdslot";
    }

    @GetMapping("/instellingen/tijdsloten/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Tijdsloten tijdsloten = tijdslotenService.getById(id);
        if (tijdsloten == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        model.addAttribute("tijdsloten", tijdsloten);
        return "/templatesInstellingen/tijdslot";
    }

    @GetMapping("/instellingen/tijdslot/delete/{id}")
    public String delete(@PathVariable int id){
        Tijdsloten tijdsloten = tijdslotenService.getById(id);
        if (tijdsloten == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        tijdslotenService.delete(id);
        return "redirect:instellingen/tijdslot";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
