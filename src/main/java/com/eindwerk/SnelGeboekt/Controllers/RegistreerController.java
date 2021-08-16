package com.eindwerk.SnelGeboekt.Controllers;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.user.User;
import com.eindwerk.SnelGeboekt.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class RegistreerController {

    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/registreer")
    public String rootHandler(Principal principal, Model model) {
        if (principal != null) {
            return ("redirect:/settings");
        }
        Organisatie organisatie = new Organisatie();
        model.addAttribute("organisatie", organisatie);
        return ("registreer");
    }
}
