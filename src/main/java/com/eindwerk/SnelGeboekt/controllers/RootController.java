package com.eindwerk.SnelGeboekt.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class RootController {

    @GetMapping("/")
    public String rootHandler(Principal principal) {
        if (principal != null) {
            return ("homeL");
        }
        return ("home");
    }
}