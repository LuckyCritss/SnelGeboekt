package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.instellingen.medewerker.MedewerkerService;
import com.eindwerk.SnelGeboekt.instellingen.optie.OptieService;
import com.eindwerk.SnelGeboekt.notification.NotificationService;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import com.eindwerk.SnelGeboekt.reservatie.*;
import com.eindwerk.SnelGeboekt.user.User;
import com.eindwerk.SnelGeboekt.user.UserService;
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
    private final UserService userService;
    private final ReservatieService reservatieService;
    private final NotificationService notificationService;
    private final ReservatieDTO reservatieDTO;

    public ReservatieController(Reservatie reservatie, MedewerkerService medewerkerService, OptieService optieService,
                                OrganisatieService organisatieService, ReservatieService reservatieService,
                                NotificationService notificationService, UserService userService, ReservatieDTO reservatieDTO) {
        this.reservatie = reservatie;
        this.medewerkerService = medewerkerService;
        this.optieService = optieService;
        this.organisatieService = organisatieService;
        this.reservatieService = reservatieService;
        this.notificationService = notificationService;
        this.userService = userService;
        this.reservatieDTO = reservatieDTO;
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
    public String processWidgetStep2Next(@ModelAttribute StepTwoData stepTwoData, Principal principal) {
        if (reservatie.getSlug() == null) {
            return "redirect:/reservatie/" + reservatie.getSlug() + "/step1";
        }
        reservatie.setStepTwoData(stepTwoData);
        if (principal != null){
            User user = userService.getUserByEmail(principal.getName());
            StepThreeData loggedUser = new StepThreeData();
            loggedUser.setEmail(user.getEmail());
            loggedUser.setName(user.getNaam());
            loggedUser.setTel(user.getGsmNummer());
            reservatie.setStepThreeData(loggedUser);
            return "redirect:/reservatie/" + reservatie.getSlug() + "/step4";
        }
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
        return "redirect:/reservatie/" + reservatie.getSlug() + "/step4";
    }

    @GetMapping("/{slug}/step4")
    public String showWidgetStep4(@PathVariable String slug, Model model) {
        if (reservatie.getSlug() == null || !reservatie.getSlug().equals(slug)) {
            return "redirect:/reservatie/" + slug + "/step1";
        }
        model.addAttribute("reservatie", reservatie);
        return "templatesReservatie/booking_step4";
    }

    @PostMapping(value = "/step4" , params = "bevestig")
    public String processWidgetStep4(Principal principal) {
        if (reservatie.getSlug() == null) {
            return "redirect:/reservatie/" + reservatie.getSlug() + "/step1";
        }
        reservatieDTO.setOrganisatie(organisatieService.getOrganisatieByName(reservatie.getSlug()));
        reservatieDTO.setDienst(reservatie.getStepOneData().getService());
        reservatieDTO.setMedewerker(medewerkerService.getMedewerkerByOrganisatieAndName(reservatieDTO.getOrganisatie() ,reservatie.getStepOneData().getEmployee()));
        reservatieDTO.setDate(reservatie.getStepTwoData().getDate());
        reservatieDTO.setTime(reservatie.getStepTwoData().getTime());
        reservatieDTO.setEmail(reservatie.getStepThreeData().email);
        reservatieDTO.setName(reservatie.getStepThreeData().name);
        reservatieDTO.setTel(reservatie.getStepThreeData().tel);
        reservatieService.save(reservatieDTO);
        notificationService.sendSuccesfullReservateie(reservatieDTO);
        return "redirect:/";
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
