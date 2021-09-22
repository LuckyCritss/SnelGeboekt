package com.eindwerk.SnelGeboekt.bookingwidget;


import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.instellingen.medewerker.MedewerkerService;
import com.eindwerk.SnelGeboekt.instellingen.optie.OptieService;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/booking/")
public class BookingController {

    private final Booking booking;

    private final MedewerkerService medewerkerService;

    private final OptieService optieService;

    private final OrganisatieService organisatieService;

    public BookingController(Booking booking, MedewerkerService medewerkerService, OptieService optieService, OrganisatieService organisatieService) {
        this.booking = booking;
        this.medewerkerService = medewerkerService;
        this.optieService = optieService;
        this.organisatieService = organisatieService;
    }

    @GetMapping("/{slug}")
    public String showWidget(@PathVariable String slug) {
        return "redirect:/booking/" + slug + "/step1";
    }

    @GetMapping("/{slug}/step1")
    public String showWidgetStep1(@PathVariable String slug, Model model) {
        if (booking.getSlug() == null || !booking.getSlug().equals(slug)) {
            booking.setSlug(slug);
            booking.setStepOneData(new StepOneData());
            booking.setStepTwoData(new StepTwoData());
            booking.setStepThreeData(new StepThreeData());
        }

        List<Integer> medewerkersId = medewerkerService.getMedewerkersIdByOrganisation(organisatieService.getOrganisatieByName(slug));
        Set<String> opties = new HashSet<String>();
        for (Integer integer : medewerkersId) {
            opties.addAll(optieService.getOptieTitelsByMedewerkersId(integer));
        }
        model.addAttribute("opties", opties);
        return "snelgeboekt/booking_step1";
    }

    @PostMapping("/step1")
    public String processWidgetStep1(@ModelAttribute StepOneData stepOneData) {
        if (booking.getSlug() == null) {
            return "redirect:/booking/" + booking.getSlug() + "/step1";
        }
        booking.setStepOneData(stepOneData);
        return "redirect:/booking/" + booking.getSlug() + "/step2";
    }

    @GetMapping("/{slug}/step2")
    public String showWidgetStep2(@PathVariable String slug, Model model) {
        if (booking.getSlug() == null || !booking.getSlug().equals(slug)) {
            return "redirect:/booking/" + slug + "/step1";
        }
        model.addAttribute("stepOneData", booking.getStepOneData());
        model.addAttribute("slug", booking.getSlug());
        return "snelgeboekt/booking_step2";
    }

    @PostMapping(value = "/step2", params = "previous")
    public String processWidgetStep2Previous() {
        return "redirect:/booking/" + booking.getSlug() + "/step1";
    }

    @PostMapping(value = "/step2", params = "time")
    public String processWidgetStep2Next(@ModelAttribute StepTwoData stepTwoData) {
        if (booking.getSlug() == null) {
            return "redirect:/booking/" + booking.getSlug() + "/step1";
        }
        booking.setStepTwoData(stepTwoData);
        return "redirect:/booking/" + booking.getSlug() + "/step3";
    }

    @GetMapping("/{slug}/step3")
    public String showWidgetStep3(@PathVariable String slug, Model model) {
        if (booking.getSlug() == null || !booking.getSlug().equals(slug)) {
            return "redirect:/booking/" + slug + "/step1";
        }
        model.addAttribute("stepThreeData", booking.getStepThreeData());
        return "snelgeboekt/booking_step3";
    }

    @PostMapping(value = "/step3", params = "previous")
    public String processWidgetStep3Previous() {
        if (booking.getSlug() == null) {
            return "redirect:/booking/" + booking.getSlug() + "/step1";
        }
        return "redirect:/booking/" + booking.getSlug() + "/step2";
    }


    @PostMapping(value = "/step3", params = "next")
    public String processWidgetStep3Next(@ModelAttribute StepTwoData stepTwoData) {
        if (booking.getSlug() == null) {
            return "redirect:/booking/" + booking.getSlug() + "/step1";
        }
        return "redirect:/booking/" + booking.getSlug() + "/step4";
    }

    @GetMapping("/{slug}/step4")
    public String showWidgetStep4(@PathVariable String slug, Model model) {
        if (booking.getSlug() == null || !booking.getSlug().equals(slug)) {
            return "redirect:/booking/" + slug + "/step1";
        }
        model.addAttribute("booking", booking);
        return "snelgeboekt/booking_step4";
    }

    @GetMapping("/{slug}/getschedule")
    public String ajaxDates(@PathVariable String slug,
                            @RequestParam String service,
                            @RequestParam String employee,
                            @RequestParam String date,
                            Model model) {
        return "snelgeboekt/fragments/booking/hours :: hours";
    }

    @GetMapping("/{slug}/getemployees")
    public String ajaxEmployees(@PathVariable String slug,
                                @RequestParam String optie,
                                Model model) {
        List<Medewerker> medewerkers = optieService.getMedewerkerByOptie(optie,organisatieService.getOrganisatieByName(slug));
        model.addAttribute("medewerkers", medewerkers);
        return "snelgeboekt/fragments/booking/employees :: employees";
    }




}
