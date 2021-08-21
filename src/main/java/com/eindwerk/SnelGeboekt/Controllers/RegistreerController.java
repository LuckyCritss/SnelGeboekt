package com.eindwerk.SnelGeboekt.Controllers;

import com.eindwerk.SnelGeboekt.notification.NotificationService;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
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
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class RegistreerController {

    private OrganisatieService organisatieService;
    private AuthenticationManager authenticationManager;
    private NotificationService notificationService;

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

    @GetMapping("/registreer")
    public String rootHandler(Principal principal, Model model) {
        if (principal != null) {
            return ("redirect:/instellingen");
        }
        Organisatie organisatie = new Organisatie();
        model.addAttribute("organisatie", organisatie);
        return ("registreer");
    }

    @PostMapping("/registreer")
    public String processForm(@Valid @ModelAttribute Organisatie organisatie, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "registreer";
        }
        try {
            organisatieService.save(organisatie);
        } catch (OrganisatieService.PasswordException e) {
            bindingResult.rejectValue("wachtWoord","user.wachtWoord",e.getMessage());
            return "registreer";
        } catch (OrganisatieService.PasswordMisMatchException e) {
            bindingResult.rejectValue("wachtWoord","password-mismatch",e.getMessage());
            return "registreer";
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("email_unique")) {
                bindingResult.rejectValue("email","user.email-unique",e.getMessage());
            }
            return "registreer";
        }
        notificationService.sendNotification(organisatie);
        authWithAuthManager(request, organisatie.getEmail(),organisatie.getWachtWoord());
        return "redirect:/instellingen";
    }

    @GetMapping("/registreer/{id}")
    public String edit(@PathVariable int id, Model model) {
        Organisatie organisatie = organisatieService.getById(id);
        if (organisatie == null) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
        model.addAttribute("organisatie", organisatie);
        return "fragments/registreer";
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
