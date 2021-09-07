package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.optie.OptieService;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.Tijdslot;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.TijdslotService;
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
    private TijdslotService tijdslotService;
    private OptieService optieService;

    @Autowired
    public void setOrganisatieService(OrganisatieService organisatieService){
        this.organisatieService = organisatieService;
    }

    @Autowired
    public void setTijdslotenService(TijdslotService tijdslotService) {
        this.tijdslotService = tijdslotService;
    }

    @Autowired
    public void setOptieService(OptieService optieService) {
        this.optieService = optieService;
    }

    @GetMapping("/instellingen/tijdslot")
    public String tijdslotHandler(Principal principal, Model model) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            List<Tijdslot> tijdsloten = tijdslotService.getAll();
            model.addAttribute("tijdloten", tijdsloten);
            return ("/templatesInstellingen/tijdslot");
        }
        return "redirect:/instellingen";
    }

    @GetMapping("/instellingen/tijdslot/add")
    public String add(Model model,Principal principal) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            model.addAttribute("tijdslot", new Tijdslot());
            return "/templatesInstellingen/addtijdsloten";
        }
        return "redirect:/instellingen";
    }

    @PostMapping("/instellingen/tijdslot/add")
    public String addForm(@Valid @ModelAttribute Tijdslot tijdslot, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "/templatesInstellingen/addtijdsloten";
        }
        try {
            tijdslot.setOrganisatie(organisatieService.getOrganisatieByEmail(principal.getName()));
            tijdslotService.saveOrUpdate(tijdslot);
        }catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("tijdsloten_unique")) {
                bindingResult.rejectValue("tijdsloten","tijdsloten-unique",e.getMessage());
            }
            return "/templatesInstellingen/addtijdsloten";
        }
        return "redirect:/instellingen/tijdslot";
    }

    @PostMapping("/instellingen/tijdslot/edit")
    public String processForm(@Valid @ModelAttribute Tijdslot tijdslot, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/templatesInstellingen/tijdslot";
        }
        tijdslotService.saveOrUpdate(tijdslot);
        return "redirect:/instellingen/tijdslot";
    }

    @GetMapping("/instellingen/tijdsloten/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Tijdslot tijdslot = tijdslotService.getById(id);
        if (tijdslot == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        model.addAttribute("tijdslot", tijdslot);
        return "/templatesInstellingen/tijdslot";
    }

    @GetMapping("/instellingen/tijdslot/delete/{id}")
    public String delete(@PathVariable int id){
        Tijdslot tijdslot = tijdslotService.getById(id);
        if (tijdslot == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        tijdslotService.delete(id);
        return "redirect:instellingen/tijdslot";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
