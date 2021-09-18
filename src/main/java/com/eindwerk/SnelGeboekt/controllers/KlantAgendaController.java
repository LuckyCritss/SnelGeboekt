package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.reservatie.Reservatie;
import com.eindwerk.SnelGeboekt.reservatie.ReservatieService;
import com.eindwerk.SnelGeboekt.user.User;
import com.eindwerk.SnelGeboekt.user.UserService;
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
public class KlantAgendaController {

    private UserService userService;
    private ReservatieService reservatieService;

    @Autowired
    public void setReservatieService(ReservatieService reservatieService) {
        this.reservatieService = reservatieService;
    }
    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/instellingen/klantagenda")
    public String agendaHandler(Principal principal, Model model) {
        if(userService.getUserByEmail(principal.getName()) != null){
            User user = userService.getUserByEmail(principal.getName());
            List<Reservatie> reservaties = reservatieService.getReservatiesByUser(user);
            model.addAttribute("reservaties", reservaties);
            return ("/templatesInstellingen/lopendereservaties");
        }
        return "redirect:/instellingen";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
