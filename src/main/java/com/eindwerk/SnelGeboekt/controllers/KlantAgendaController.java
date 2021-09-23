package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.reservatie.Reservatie;
import com.eindwerk.SnelGeboekt.reservatie.ReservatieDTO;
import com.eindwerk.SnelGeboekt.reservatie.ReservatieService;
import com.eindwerk.SnelGeboekt.user.User;
import com.eindwerk.SnelGeboekt.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

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
            List<ReservatieDTO> reservaties = reservatieService.getReservatiesByEmail(user.getEmail());
            model.addAttribute("reservaties", reservaties);
            return ("templatesInstellingen/lopendereservaties");
        }
        return "redirect:/instellingen";
    }

    @GetMapping("/instellingen/klantagenda/delete/{id}")
    public String delete(@PathVariable int id){
        ReservatieDTO reservatieDTO = reservatieService.getReservatieById(id);
        if (reservatieDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        reservatieService.delete(id);
        return "redirect:/instellingen/klantagenda";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
