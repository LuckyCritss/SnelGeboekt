package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.instellingen.medewerker.MedewerkerService;
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
    private MedewerkerService medewerkerService;

    @Autowired
    public void setOrganisatieService(OrganisatieService organisatieService){
        this.organisatieService = organisatieService;
    }

    @Autowired
    public void setMedewerkerService(MedewerkerService medewerkerService) {
        this.medewerkerService = medewerkerService;
    }

    @Autowired
    public void setTijdslotService(TijdslotService tijdslotService) {
        this.tijdslotService = tijdslotService;
    }


    @GetMapping("/instellingen/medewerker/{id}/tijdslot")
    public String tijdslotHandler(@PathVariable int id, Principal principal, Model model) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            List<Tijdslot> tijdsloten = tijdslotService.getTijdslotenByMedewerker(medewerkerService.getMedewerkerById(id));
            model.addAttribute("tijdloten", tijdsloten);
            return ("/templatesInstellingen/tijdslot");
        }
        return "redirect:/instellingen";
    }

    @GetMapping("/instellingen/medewerker/{id}/tijdslot/add")
    public String add(@PathVariable int id, Model model,Principal principal) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            model.addAttribute("tijdslot", new Tijdslot());
            return "/templatesInstellingen/addtijdsloten";
        }
        return "redirect:/instellingen";
    }

    @PostMapping("/instellingen/medewerker/{id}/tijdslot/add")
    public String addForm(@PathVariable int id, @Valid @ModelAttribute Tijdslot tijdslot, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "/templatesInstellingen/addtijdsloten";
        }
        try {
            tijdslot.setMedewerker(medewerkerService.getMedewerkerById(id));
            tijdslotService.saveOrUpdate(tijdslot);
        }catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("tijdslot_unique")) {
                bindingResult.rejectValue("tijdslot","tijdslot-unique",e.getMessage());
            }
            return "/templatesInstellingen/addtijdsloten";
        }
        return "redirect:/instellingen/tijdslot";
    }

    @GetMapping("/instellingen/medewerker/{id}/tijdsloten/edit")
    public String edit(@PathVariable int id, Model model) {
        Tijdslot tijdslot = tijdslotService.getById(id);
        if (tijdslot == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        model.addAttribute("tijdslot", tijdslot);
        return "/templatesInstellingen/addtijdslot";
    }

    @PostMapping("/instellingen/medewerker/{id}/tijdslot/edit")
    public String processForm(@PathVariable int id, @Valid @ModelAttribute Tijdslot tijdslot, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/templatesInstellingen/tijdslot";
        }
        tijdslotService.saveOrUpdate(tijdslot);
        return "redirect:/instellingen/tijdslot";
    }



    @GetMapping("/instellingen/medewerker/{id}/tijdslot/delete")
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
