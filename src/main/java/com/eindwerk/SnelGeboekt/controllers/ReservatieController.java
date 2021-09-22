package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.instellingen.medewerker.MedewerkerService;
import com.eindwerk.SnelGeboekt.instellingen.optie.OptieService;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.Tijdslot;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.TijdslotService;
import com.eindwerk.SnelGeboekt.notification.NotificationService;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import com.eindwerk.SnelGeboekt.reservatie.*;
import com.eindwerk.SnelGeboekt.user.User;
import com.eindwerk.SnelGeboekt.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

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
    private final TijdslotService tijdslotService;

    public ReservatieController(Reservatie reservatie, MedewerkerService medewerkerService, OptieService optieService,
                                OrganisatieService organisatieService, ReservatieService reservatieService,
                                NotificationService notificationService, UserService userService, ReservatieDTO reservatieDTO,
                                TijdslotService tijdslotService) {
        this.reservatie = reservatie;
        this.medewerkerService = medewerkerService;
        this.optieService = optieService;
        this.organisatieService = organisatieService;
        this.reservatieService = reservatieService;
        this.notificationService = notificationService;
        this.userService = userService;
        this.reservatieDTO = reservatieDTO;
        this.tijdslotService = tijdslotService;
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
        model.addAttribute("slug", slug);
        model.addAttribute("opties", opties);
        return "templatesReservatie/booking_step1";
    }

    @PostMapping(value = "/step1" , params = "cancel")
    public String processWidgetStep1Previous() {
        return "redirect:/reservatielijst";
    }

    @PostMapping(value = "/step1" , params = "next")
    public String processWidgetStep1Next(@ModelAttribute StepOneData stepOneData) {
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
        model.addAttribute("slug", slug);
        return "templatesReservatie/booking_step2";
    }

    @PostMapping(value = "/step2", params = "time")
    public String processWidgetStep2Next(@ModelAttribute StepTwoData stepTwoData, Principal principal) {
        if (reservatie.getSlug() == null) {
            return "redirect:/reservatie/" + reservatie.getSlug() + "/step1";
        }
        if(stepTwoData.getTime().startsWith(",")){
            String newTime =  stepTwoData.getTime().substring(1);
            stepTwoData.setTime(newTime);
        }
        else if (stepTwoData.getTime().endsWith(",")){
            String newTime =  stepTwoData.getTime().substring(0,stepTwoData.getTime().length()-1);
            stepTwoData.setTime(newTime);
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
        model.addAttribute("slug", slug);
        model.addAttribute("stepThreeData", reservatie.getStepThreeData());
        return "templatesReservatie/booking_step3";
    }

    @PostMapping(value = "/step3", params = "previous")
    public String processWidgetStep3Previous() {
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
        model.addAttribute("slug", slug);
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
        if (principal != null){
            return "redirect:/instellingen/klantagenda";
        }
        return "redirect:/";
    }

    @GetMapping("/{slug}/getschedule")
    public String ajaxDates(@PathVariable String slug,
                            @RequestParam String service,
                            @RequestParam String employee,
                            @RequestParam String date,
                            Model model) throws ParseException {
        Medewerker medewerker = medewerkerService.getMedewerkerByOrganisatieAndName(organisatieService.getOrganisatieByName(slug),employee);
        int duration = optieService.getDuurOptie(medewerker.getNaam(), service);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date dateday = formatter.parse(date);
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateday);
        if (dayOfWeek.equals("Monday")){
            dayOfWeek = "Maandag";
        }if (dayOfWeek.equals("Tuesday")){
            dayOfWeek = "Dinsdag";
        }
        if (dayOfWeek.equals("Wednesday")){
            dayOfWeek = "Woensdag";
        }if (dayOfWeek.equals("Thursday")){
            dayOfWeek = "Donderdag";
        }
        if (dayOfWeek.equals("Friday")){
            dayOfWeek = "Vrijdag";
        }if (dayOfWeek.equals("Saturday")){
            dayOfWeek = "Zaterdag";
        }
        if (dayOfWeek.equals("Sunday")){
            dayOfWeek = "Zondag";
        }

        List<String> openTijdsloten = new ArrayList<>();
        List<Tijdslot> tijdsloten = tijdslotService.getTijdslotenByMedewerkerAndDay(medewerker , dayOfWeek);
        for (Tijdslot tijdslot : tijdsloten){
            LocalTime start = tijdslot.getStart();
            LocalTime volgende = start;
            LocalTime einde = tijdslot.getEinde();
            for (int i = 0; i < 40;i++) {
                if(i == 0){
                    volgende = start.plusMinutes(duration);
                    openTijdsloten.add(String.valueOf(start));
                    openTijdsloten.add(String.valueOf(volgende));
                }else if (volgende.plusMinutes(duration).isBefore(einde) && volgende.plusMinutes(duration).compareTo(einde) <= 0){
                    volgende = volgende.plusMinutes(duration);
                    openTijdsloten.add(String.valueOf(volgende));
                }
            }
        }
        model.addAttribute("tijdsloten" , openTijdsloten);
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
