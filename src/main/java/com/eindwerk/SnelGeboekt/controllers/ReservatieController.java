package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.instellingen.medewerker.MedewerkerService;
import com.eindwerk.SnelGeboekt.instellingen.optie.OptieService;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import com.eindwerk.SnelGeboekt.reservatie.*;
import com.eindwerk.SnelGeboekt.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/reservatie/")
public class ReservatieController {

    private final Reservatie reservatie;

    private final MedewerkerService medewerkerService;

    private final OptieService optieService;

    private final OrganisatieService organisatieService;

    private final ReservatieService reservatieService;

    public ReservatieController(Reservatie reservatie, MedewerkerService medewerkerService, OptieService optieService, OrganisatieService organisatieService, ReservatieService reservatieService) {
        this.reservatie = reservatie;
        this.medewerkerService = medewerkerService;
        this.optieService = optieService;
        this.organisatieService = organisatieService;
        this.reservatieService = reservatieService;
    }

    @GetMapping("/{slug}")
    public String showWidget(@PathVariable String slug) {
        return "redirect:/reservatie/" + slug + "/step1";
    }

    @GetMapping("/{slug}/step1")
    public String showWidgetStep1(@PathVariable String slug, Model model) {
        if (reservatie.getSlug() == null || !reservatie.getSlug().equals(slug)) {
            reservatie.setSlug(slug);
            reservatie.setStepOneData(new StepOneData());
            reservatie.setStepTwoData(new StepTwoData());
            reservatie.setStepThreeData(new StepThreeData());
        }

        List<Integer> medewerkersId = medewerkerService.getMedewerkersIdByOrganisation(organisatieService.getOrganisatieByName(slug));
        Set<String> opties = new HashSet<String>();
        for (Integer integer : medewerkersId) {
            opties.addAll(optieService.getOptieTitelsByMedewerkersId(integer));
        }
        model.addAttribute("opties", opties);
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
        if (reservatie.getSlug() == null || !reservatie.getSlug().equals(slug)) {
            return "redirect:/reservatie/" + slug + "/step1";
        }
        model.addAttribute("stepOneData", reservatie.getStepOneData());
        model.addAttribute("slug", reservatie.getSlug());
        return "templatesReservatie/booking_step2";
    }

    @PostMapping(value = "/step2", params = "previous")
    public String processWidgetStep2Previous() {
        return "redirect:/reservatie/" + reservatie.getSlug() + "/step1";
    }

    @PostMapping(value = "/step2", params = "time")
    public String processWidgetStep2Next(@ModelAttribute StepTwoData stepTwoData) {
        if (reservatie.getSlug() == null) {
            return "redirect:/reservatie/" + reservatie.getSlug() + "/step1";
        }
        reservatie.setStepTwoData(stepTwoData);
        return "redirect:/reservatie/" + reservatie.getSlug() + "/step3";
    }

    @GetMapping("/{slug}/step3")
    public String showWidgetStep3(@PathVariable String slug, Model model) {
        if (reservatie.getSlug() == null || !reservatie.getSlug().equals(slug)) {
            return "redirect:/reservatie/" + slug + "/step1";
        }
        model.addAttribute("stepThreeData", reservatie.getStepThreeData());
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
    public String processWidgetStep3Next(@ModelAttribute StepThreeData stepThreeData) {
        if (reservatie.getSlug() == null) {
            return "redirect:/reservatie/" + reservatie.getSlug() + "/step1";
        }
        reservatie.setStepThreeData(stepThreeData);
        ReservatieDTO reservatieDTO = new ReservatieDTO();
        reservatieDTO.setOrganisatie(organisatieService.getOrganisatieByName(reservatie.getSlug()));
        reservatieDTO.setDienst(reservatie.getStepOneData().getService());
        reservatieDTO.setMedewerker(medewerkerService.getMedewerkerByOrganisatieAndName(reservatieDTO.getOrganisatie() ,reservatie.getStepOneData().getEmployee()));
        reservatieDTO.setDate(reservatie.getStepTwoData().getDate());
        reservatieDTO.setTime(reservatie.getStepTwoData().getTime());
        reservatieDTO.setEmail(reservatie.getStepThreeData().email);
        reservatieDTO.setName(reservatie.getStepThreeData().name);
        reservatieDTO.setTel(reservatie.getStepThreeData().tel);
        reservatieService.save(reservatieDTO);
        return "redirect:/reservatie/" + reservatie.getSlug() + "/step4";
    }

    @GetMapping("/{slug}/step4")
    public String showWidgetStep4(@PathVariable String slug, Model model) {
        if (reservatie.getSlug() == null || !reservatie.getSlug().equals(slug)) {
            return "redirect:/reservatie/" + slug + "/step1";
        }
        model.addAttribute("booking", reservatie);
        return "templatesReservatie/booking_step4";
    }

    @GetMapping("/{slug}/getschedule")
    public String ajaxDates(@PathVariable String slug,
                            @RequestParam String service,
                            @RequestParam String employee,
                            @RequestParam String date,
                            Model model) {
        return "fragmentsReservatie/hours :: hours";
    }

    @GetMapping("/{slug}/getemployees")
    public String ajaxEmployees(@PathVariable String slug,
                                @RequestParam String optie,
                                Model model) {
        List<Medewerker> medewerkers = optieService.getMedewerkerByOptie(optie,organisatieService.getOrganisatieByName(slug));
        model.addAttribute("medewerkers", medewerkers);
        return "fragmentsReservatie/employees :: employees";
    }




}
