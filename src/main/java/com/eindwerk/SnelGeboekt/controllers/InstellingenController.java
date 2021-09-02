package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.notification.NotificationService;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import com.eindwerk.SnelGeboekt.user.User;
import com.eindwerk.SnelGeboekt.user.UserService;
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

    private UserService userService;
    private OrganisatieService organisatieService;
    private NotificationService notificationService;
    private BindingResult bindingResult;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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
        if(userService.getUserByEmail(principal.getName()) != null){
            User user = userService.getUserByEmail(principal.getName());
            model.addAttribute("user", user);
            return ("/templatesUser/instellingenUser");
        }
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null ){
            Organisatie organisatie = organisatieService.getOrganisatieByEmail(principal.getName());
            model.addAttribute("organisatie", organisatie);
            return ("instellingenOrg");
        }
        return "redirect:/login";
    }

    @PostMapping("/instellingen")
    public String processForm(@Valid @ModelAttribute User user, @Valid @ModelAttribute Organisatie organisatie, Principal principal, BindingResult bindingResult) {
        if(userService.getUserByEmail(principal.getName()) != null){
            userForm(user, bindingResult);
        }
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null ){
            organisatieForm(organisatie, bindingResult);
        }
        return "redirect:/login";
    }

    public String organisatieForm(@Valid @ModelAttribute Organisatie organisatie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "instellingenOrg";
        }
        try {
            organisatieService.save(organisatie);
        } catch (OrganisatieService.PasswordException e) {
            bindingResult.rejectValue("wachtWoord","org.wachtWoord",e.getMessage());
            return "instellingenOrg";
        } catch (OrganisatieService.PasswordMisMatchException e) {
            bindingResult.rejectValue("wachtWoord","password-mismatch",e.getMessage());
            return "instellingenOrg";
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("email_unique")) {
                bindingResult.rejectValue("email","org.email-unique",e.getMessage());
            }
            return "instellingenOrg";
        }
        notificationService.sendAccountUpdateOrganisatie(organisatie);
        return "redirect:/instellingen";
    }

    public String userForm(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "instellingenUser";
        }
        try {
            userService.save(user);
        } catch (UserService.PasswordException e) {
            bindingResult.rejectValue("wachtWoord","user.wachtWoord",e.getMessage());
            return "instellingenUser";
        } catch (UserService.PasswordMisMatchException e) {
            bindingResult.rejectValue("wachtWoord","password-mismatch",e.getMessage());
            return "instellingenUser";
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("email_unique")) {
                bindingResult.rejectValue("email","user.email-unique",e.getMessage());
            }
            return "instellingenUser";
        }
        notificationService.sendAccountUpdateUser(user);
        return "redirect:/instellingen";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
