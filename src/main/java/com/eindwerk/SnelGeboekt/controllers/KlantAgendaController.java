package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import com.eindwerk.SnelGeboekt.user.User;
import com.eindwerk.SnelGeboekt.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

import java.security.Principal;

public class KlantAgendaController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/instellingen/klantagenda")
    public String agendaHandler(Principal principal, Model model) {
        if(userService.getUserByEmail(principal.getName()) != null){
            User user = userService.getUserByEmail(principal.getName());
            model.addAttribute("user", user);
            return ("/templatesInstellingen/lopendereservaties");
        }
        return "redirect:/instellingen";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
