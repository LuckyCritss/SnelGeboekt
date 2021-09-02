package com.eindwerk.SnelGeboekt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class LoginController {

    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
}

    @GetMapping("/login")
    public String rootHandler(Principal principal ,HttpServletRequest request) {
        if (principal == null ) {
            //authWithAuthManager(request, "admin", "admin");
        }
        if (principal != null) {
            return ("redirect:/instellingen");
        }
        return ("login");
    }

    @GetMapping("/templatesUser/boeking")
    public String onlineReservatie(Principal principal ,HttpServletRequest request) {
        return ("/templatesUser/boeking");
    }


    public void authWithAuthManager(HttpServletRequest request, String email, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
