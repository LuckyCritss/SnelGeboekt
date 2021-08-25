package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.notification.NotificationService;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class InstellingenController {

    private OrganisatieService organisatieService;
    private NotificationService notificationService;

    @Autowired
    public void setOrganisatieService(OrganisatieService organisatieService){
        this.organisatieService = organisatieService;
    }

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/instellingen")
    public String rootHandler(Principal principal, Model model) {
        if (principal == null) {
            return ("redirect:/login");
        }
        Organisatie organisatie = organisatieService.getOrganisatieByEmail(principal.getName());
        model.addAttribute("organisatie", organisatie);
        return ("instellingen");
    }

    @PostMapping("/instellingen")
    public String processForm(@Valid @ModelAttribute Organisatie organisatie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "instellingen";
        }
        try {
            organisatieService.save(organisatie);
        } catch (OrganisatieService.PasswordException e) {
            bindingResult.rejectValue("wachtWoord","user.wachtWoord",e.getMessage());
            return "instellingen";
        } catch (OrganisatieService.PasswordMisMatchException e) {
            bindingResult.rejectValue("wachtWoord","password-mismatch",e.getMessage());
            return "instellingen";
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("email_unique")) {
                bindingResult.rejectValue("email","user.email-unique",e.getMessage());
            }
            return "instellingen";
        }
        notificationService.sendAccountUpdate(organisatie);
        return "redirect:/instellingen";
    }

    @GetMapping("/instellingen/keuzemogelijkheden")
    public String keuzemogelijkhedenHandler(Principal principal, Model model) {
        if (principal == null) {
            return ("redirect:/login");
        }
        Organisatie organisatie = organisatieService.getOrganisatieByEmail(principal.getName());
        model.addAttribute("organisatie", organisatie);
        return ("keuzemogelijkheden");
    }

    @GetMapping("/instellingen/agenda")
    public String agendaHandler(Principal principal, Model model) {
        if (principal == null) {
            return ("redirect:/login");
        }
        Organisatie organisatie = organisatieService.getOrganisatieByEmail(principal.getName());
        model.addAttribute("organisatie", organisatie);
        return ("kalender");
    }

    @GetMapping("/instellingen/tijdslot")
    public String tijdslotnHandler(Principal principal, Model model) {
        if (principal == null) {
            return ("redirect:/login");
        }
        Organisatie organisatie = organisatieService.getOrganisatieByEmail(principal.getName());
        model.addAttribute("organisatie", organisatie);
        return ("tijdslot");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
