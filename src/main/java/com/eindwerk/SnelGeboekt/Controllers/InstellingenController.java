package com.eindwerk.SnelGeboekt.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class InstellingenController {

    @GetMapping("/instellingen")
    public String rootHandler(Principal principal) {

        return ("instellingen");
    }
}
