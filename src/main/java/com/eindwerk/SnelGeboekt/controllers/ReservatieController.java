package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.notification.NotificationService;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import com.eindwerk.SnelGeboekt.reservatie.ReservatieService;
import com.eindwerk.SnelGeboekt.user.User;
import com.eindwerk.SnelGeboekt.user.UserService;
import com.eindwerk.SnelGeboekt.reservatie.Reservatie;
import com.eindwerk.SnelGeboekt.reservatie.StepOneData;
import com.eindwerk.SnelGeboekt.reservatie.StepTwoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/reservatie/")
public class ReservatieController {

    private ReservatieService reservatieService;
    private OrganisatieService organisatieService;
    private UserService userService;
    private NotificationService notificationService;
    private Reservatie reservatie;

    @Autowired
    public void setReservatieService(ReservatieService reservatieService) {
        this.reservatieService = reservatieService;
    }

    @Autowired
    public void setOrganisatieService(OrganisatieService organisatieService) {
        this.organisatieService = organisatieService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @GetMapping("/{slug}")
    public String showWidget(@PathVariable String slug) {
        if(organisatieService.getOrganisatieByName(slug) == null){
            return "redirect:/templatesUser/boeking";
        }
        reservatie = new Reservatie();
        reservatie.setSlug(slug);
        return "redirect:/reservatie/" + slug + "/step1";
    }


    @GetMapping("/{slug}/step1")
    public String showWidgetStep1(@PathVariable String slug, Model model) {
        if (reservatie == null || reservatie.getSlug() == null || !reservatie.getSlug().equals(slug)) {
            return "redirect:/reservatie/" + slug;
        }
        model.addAttribute("stepOneData", new StepOneData());
        return "templatesReservatie/booking_step1";
    }


    @PostMapping("/step1")
    public String processWidgetStep1(@ModelAttribute StepOneData stepOneData) {
        reservatie.setStepOneData(stepOneData);
        return "redirect:/reservatie/" + reservatie.getSlug() + "/step2";
    }


    @GetMapping("/{slug}/step2")
    public String showWidgetStep2(@PathVariable String slug, Model model) {
        if (reservatie == null || reservatie.getSlug() == null || !reservatie.getSlug().equals(slug)) {
            return "redirect:/reservatie/" + slug;
        }

        model.addAttribute("slug", reservatie.getSlug());
        model.addAttribute("stepTwoData", new StepTwoData());
        return "templatesReservatie/booking_step2";
    }


    @PostMapping(value = "/step2", params = "previous")
    public String processWidgetStep2Previous() {
        return "redirect:/reservatie/" + reservatie.getSlug() + "/step1";
    }


    @PostMapping(value = "/step2", params = "select")
    public String processWidgetStep2Next(@ModelAttribute StepTwoData stepTwoData, @RequestParam("select") String select) {
        reservatie.setStepTwoData(stepTwoData);
        return "redirect:/reservatie/" + reservatie.getSlug() + "/step3";
    }


    @GetMapping("/{slug}/step3")
    public String showWidgetStep3(@PathVariable String slug, Model model, Principal principal) {
        if (reservatie == null || reservatie.getSlug() == null || !reservatie.getSlug().equals(slug)) {
            return "redirect:/reservatie/" + slug;
        }
        if(principal != null){
            reservatie.setUser(userService.getUserByEmail(principal.getName()));
           return "redirect:/reservatie/" + reservatie.getSlug() + "/step4";
        }
        model.addAttribute("user", new User());
        return "templatesReservatie/booking_step3";
    }


    @PostMapping(value = "/step3", params = "previous")
    public String processWidgetStep3Previous() {
        return "redirect:/reservatie/" + reservatie.getSlug() + "/step2";
    }


    @PostMapping(value = "/step3", params = "next")
    public String processWidgetStep3Next(@ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "templatesReservatie/booking_step3";
        }
        if(user.getWachtWoord() != null){
            try {
                userService.save(user);
            } catch (UserService.PasswordException e) {
                bindingResult.rejectValue("wachtWoord","user.wachtWoord",e.getMessage());
                return "templatesReservatie/booking_step3";
            } catch (UserService.PasswordMisMatchException e) {
                bindingResult.rejectValue("wachtWoord","password-mismatch",e.getMessage());
                return "templatesReservatie/booking_step3";
            } catch (DataIntegrityViolationException e) {
                if (e.getMessage().contains("email_unique")) {
                    bindingResult.rejectValue("email","user.email-unique",e.getMessage());
                }
                return "templatesReservatie/booking_step3";
            }
            notificationService.sendAccountRegistrationUser(user);
        }
        reservatie.setUser(user);

        return "redirect:/reservatie/" + reservatie.getSlug() + "/step4";
    }


    @GetMapping("/{slug}/step4")
    public String showWidgetStep4(@PathVariable String slug, Model model) {
        if (reservatie == null || reservatie.getSlug() == null || !reservatie.getSlug().equals(slug)) {
            return "redirect:/reservatie/" + slug;
        }
        model.addAttribute("user", reservatie.getUser());
        return "templatesReservatie/booking_step4";
    }

    @PostMapping("/{slug}/step4")
    public String processStep4(@PathVariable String slug,Principal principal) {
        if(principal != null){
            reservatie.setUser(userService.getUserByEmail(principal.getName()));
            return "redirect:/instellingen";
        }
        else{
            return "redirect:/";
        }
    }


    @GetMapping("/{slug}/getschedule")
    public String ajaxDates(@PathVariable String slug, @RequestParam String date, Model model) {
        return "fragmentsReservatie/hours";
    }


}
