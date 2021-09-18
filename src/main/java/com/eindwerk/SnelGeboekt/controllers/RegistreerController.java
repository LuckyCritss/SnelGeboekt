package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.notification.NotificationService;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import com.eindwerk.SnelGeboekt.user.User;
import com.eindwerk.SnelGeboekt.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class RegistreerController {

    private OrganisatieService organisatieService;
    private AuthenticationManager authenticationManager;
    private NotificationService notificationService;
    private UserService userService;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setOrganisatieService(OrganisatieService organisatieService) {
        this.organisatieService = organisatieService;
    }
    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registreer")
    public String rootHandler(Principal principal) {
        if (principal != null) {
            return ("redirect:/instellingen");
        }
        return ("registreer");
    }

    @GetMapping("/registreer/organisatie")
    public String organisatieHandler(Principal principal, Model model) {
        if (principal != null) {
            return ("redirect:/instellingen");
        }
        model.addAttribute("organisatie", new Organisatie());
        return ("registreerOrganisatie");
    }

    @PostMapping("/registreer/organisatie")
    public String processForm(Principal principal, @Valid @ModelAttribute Organisatie organisatie, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors() || userService.getUserByEmail(organisatie.getEmail())!= null || organisatieService.getOrganisatieByEmail(organisatie.getEmail())!= null) {
            return "registreerOrganisatie";
        }
        try {
            organisatieService.save(organisatie);
        } catch (OrganisatieService.PasswordException e) {
            bindingResult.rejectValue("wachtWoord","user.wachtWoord",e.getMessage());
            return "registreerOrganisatie";
        } catch (OrganisatieService.PasswordMisMatchException e) {
            bindingResult.rejectValue("wachtWoord","password-mismatch",e.getMessage());
            return "registreerOrganisatie";
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("email_unique")) {
                bindingResult.rejectValue("email","user.email-unique",e.getMessage());
            }
            return "registreerOrganisatie";
        }
        notificationService.sendAccountRegistrationOrganisatie(organisatie);
        authWithAuthManager(request, organisatie.getEmail(),organisatie.getWachtWoord());
        return "redirect:/instellingen";
    }

    @GetMapping("/registreer/gebruiker")
    public String gebruikerHandler(Principal principal, Model model) {
        if (principal != null) {
            return ("redirect:/instellingen");
        }
        model.addAttribute("user", new User());
        return "registreerGebruiker";
    }

    @PostMapping("/registreer/gebruiker")
    public String processForm(Principal principal, @Valid @ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors() || userService.getUserByEmail(user.getEmail())!= null || organisatieService.getOrganisatieByEmail(user.getEmail())!= null) {
            return "registreerGebruiker";
        }
        try {
            userService.save(user);
        } catch (UserService.PasswordException e) {
            bindingResult.rejectValue("wachtWoord","user.wachtWoord",e.getMessage());
            return "registreerGebruiker";
        } catch (UserService.PasswordMisMatchException e) {
            bindingResult.rejectValue("wachtWoord","password-mismatch",e.getMessage());
            return "registreerGebruiker";
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("email_unique")) {
                bindingResult.rejectValue("email","user.email-unique",e.getMessage());
            }
            return "registreerGebruiker";
        }
        notificationService.sendAccountRegistrationUser(user);
        authWithAuthManager(request, user.getEmail(),user.getWachtWoord());
        return "redirect:/instellingen";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    public void authWithAuthManager(HttpServletRequest request, String email, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
