package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import com.eindwerk.SnelGeboekt.reservatie.Reservatie;
import com.eindwerk.SnelGeboekt.reservatie.StepOneData;
import com.eindwerk.SnelGeboekt.reservatie.StepThreeData;
import com.eindwerk.SnelGeboekt.reservatie.StepTwoData;
import com.eindwerk.SnelGeboekt.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Access;

@Controller
@RequestMapping("/reservatie/")
public class ReservatieController {

    private final Reservatie reservatie;
    private OrganisatieService organisatieService;

    @Autowired
    public void setOrganisatieService(OrganisatieService organisatieService) {
        this.organisatieService = organisatieService;
    }

    public ReservatieController(Reservatie reservatie) {
        this.reservatie = reservatie;
    }

    @GetMapping("/{slug}")
    public String showWidget(@PathVariable String slug) {
        if(organisatieService.getOrganisatieByName(slug) == null){
            return "redirect:/templatesUser/boeking";
        }
        return "redirect:/reservatie/" + slug + "/step1";
    }

    @GetMapping("/{slug}/step1")
    public String showWidgetStep1(@PathVariable String slug, Model model) {
        if(organisatieService.getOrganisatieByName(slug) == null){
            return "redirect:/templatesUser/boeking";
        }
        if (reservatie.getSlug() == null || !reservatie.getSlug().equals(slug)) {
            reservatie.setSlug(slug);
            reservatie.setStepOneData(new StepOneData());
            reservatie.setStepTwoData(new StepTwoData());
            reservatie.setUser(new User());
        }
        model.addAttribute("stepOneData", reservatie.getStepOneData());
        return "templatesReservatie/booking_step1";
    }

    @PostMapping("/step1")
    public String processWidgetStep1(@ModelAttribute StepOneData stepOneData) {
        if (reservatie.getSlug() == null) {
            return "redirect:/reservatie/" + reservatie.getSlug() + "/step1";
        }
        reservatie.setStepOneData(stepOneData);
        return "redirect:/reservatie/" + reservatie.getSlug() + "/step2";
    }

    @GetMapping("/{slug}/step2")
    public String showWidgetStep2(@PathVariable String slug, Model model) {
        if(organisatieService.getOrganisatieByName(slug) == null){
            return "redirect:/templatesUser/boeking";
        }
        if (reservatie.getSlug() == null || !reservatie.getSlug().equals(slug)) {
            return "redirect:/reservatie/" + slug + "/step1";
        }
        model.addAttribute("slug", reservatie.getSlug());
        model.addAttribute("stepOneData", reservatie.getStepTwoData());
        return "templatesReservatie/booking_step2";
    }

    @PostMapping(value = "/step2", params = "previous")
    public String processWidgetStep2Previous() {
        return "redirect:/reservatie/" + reservatie.getSlug() + "/step1";
    }

    @PostMapping(value = "/step2", params = "select")
    public String processWidgetStep2Next(@ModelAttribute StepTwoData stepTwoData, @RequestParam("select") String select) {
        if (reservatie.getSlug() == null) {
            return "redirect:/reservatie/" + reservatie.getSlug() + "/step1";
        }
        return "redirect:/reservatie/" + reservatie.getSlug() + "/step3";
    }

    @GetMapping("/{slug}/step3")
    public String showWidgetStep3(@PathVariable String slug, Model model) {
        if(organisatieService.getOrganisatieByName(slug) == null){
            return "redirect:/templatesUser/boeking";
        }
        if (reservatie.getSlug() == null || !reservatie.getSlug().equals(slug)) {
            return "redirect:/reservatie/" + slug + "/step1";
        }
        model.addAttribute("user", reservatie.getUser());
        return "templatesReservatie/booking_step3";
    }

    @PostMapping(value = "/step3", params = "previous")
    public String processWidgetStep3Previous() {
        if (reservatie.getSlug() == null) {
            return "redirect:/reservatie/" + reservatie.getSlug() + "/step1";
        }
        return "redirect:/reservatie/" + reservatie.getSlug() + "/step2";
    }


    @PostMapping(value = "/step3", params = "next")
    public String processWidgetStep3Next(@ModelAttribute StepTwoData stepTwoData) {
        if (reservatie.getSlug() == null) {
            return "redirect:/reservatie/" + reservatie.getSlug() + "/step1";
        }
        return "redirect:/reservatie/" + reservatie.getSlug() + "/step4";
    }

    @GetMapping("/{slug}/step4")
    public String showWidgetStep4(@PathVariable String slug, Model model) {
        if(organisatieService.getOrganisatieByName(slug) == null){
            return "redirect:/templatesUser/boeking";
        }
        if (reservatie.getSlug() == null || !reservatie.getSlug().equals(slug)) {
            return "redirect:/reservatie/" + slug + "/step1";
        }
        model.addAttribute("reservatie", reservatie);
        return "templatesReservatie/booking_step4";
    }

    @GetMapping("/{slug}/getschedule")
    public String ajaxDates(@PathVariable String slug, @RequestParam String date, Model model) {
        return "fragmentsReservatie/hours";
    }



}
