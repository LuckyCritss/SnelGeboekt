package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.instellingen.medewerker.MedewerkerService;
import com.eindwerk.SnelGeboekt.instellingen.optie.Optie;
import com.eindwerk.SnelGeboekt.instellingen.optie.OptieService;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.Tijdslot;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.TijdslotService;
import com.eindwerk.SnelGeboekt.notification.NotificationService;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import com.eindwerk.SnelGeboekt.reservatie.ReservatieDTO;
import com.eindwerk.SnelGeboekt.reservatie.ReservatieService;
import com.eindwerk.SnelGeboekt.user.User;
import com.eindwerk.SnelGeboekt.user.UserService;
import com.eindwerk.SnelGeboekt.reservatie.Reservatie;
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
import java.util.*;

@Controller
@RequestMapping("/reservatie/")
@SessionAttributes("reservatieDTO")
public class ReservatieController {

    private MedewerkerService medewerkerService;
    private ReservatieService reservatieService;
    private OrganisatieService organisatieService;
    private UserService userService;
    private NotificationService notificationService;
    private Reservatie reservatie;
    private OptieService optieService;
    private TijdslotService tijdslotService;
    private ReservatieDTO reservatieDTO;

    @Autowired
    public void setReservatieDTO(ReservatieDTO reservatieDTO) {
        this.reservatieDTO = reservatieDTO;
    }
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
    @Autowired
    public void setOptieService(OptieService optieService) {
        this.optieService = optieService;
    }
    @Autowired
    public void setTijdslotService(TijdslotService tijdslotService) {
        this.tijdslotService = tijdslotService;
    }
    @Autowired
    public void setMedewerkerService(MedewerkerService medewerkerService) {
        this.medewerkerService = medewerkerService;
    }

    @GetMapping("/{slug}")
    public String showWidget(@PathVariable String slug) {
        if(organisatieService.getOrganisatieByName(slug) == null){
            return "redirect:/templatesUser/boeking";
        }
        reservatieDTO = new ReservatieDTO();
        reservatieDTO.setOrganisatie(organisatieService.getOrganisatieByName(slug));
        return "redirect:/reservatie/" + reservatieDTO.getOrganisatie().getBedrijfsNaam() + "/step1";
    }

    @GetMapping("/{slug}/step1")
    public String showWidgetStep1(@PathVariable String slug, Model model) {
        if (reservatieDTO == null || reservatieDTO.getOrganisatie().getBedrijfsNaam() == null || !reservatieDTO.getOrganisatie().getBedrijfsNaam().equals(slug)) {
            return "redirect:/reservatie/" + slug;
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

    @PostMapping(value = "/step1", params = "cancel")
    public String processWidgetStepCancel() {
        return "redirect:/reservatielijst";
    }

    @PostMapping(value = "/step1", params = "next")
    public String processWidgetStep1(@ModelAttribute String optie, @ModelAttribute Medewerker medewerker) {
        return "redirect:/reservatie/" + reservatieDTO.getOrganisatie().getBedrijfsNaam() + "/step2";
    }

    @GetMapping("/{slug}/step2")
    public String showWidgetStep2(@PathVariable String slug, Model model) {
        if (reservatieDTO == null || reservatieDTO.getOrganisatie().getBedrijfsNaam() == null || !reservatieDTO.getOrganisatie().getBedrijfsNaam().equals(slug)) {
            return "redirect:/reservatie/" + slug;
        }
       //model.addAttribute("tijdslot", tijdslot);
        return "templatesReservatie/booking_step2";
    }

    @PostMapping(value = "/step2", params = "previous")
    public String processWidgetStep2Previous() {
        return "redirect:/reservatie/" + reservatieDTO.getOrganisatie().getBedrijfsNaam() + "/step1";
    }

    @PostMapping(value = "/step2", params = "select")
    public String processWidgetStep2Next(@ModelAttribute Tijdslot tijdslot, @RequestParam("select") String select) {
        reservatieDTO.setTijdslot(tijdslot);
        return "redirect:/reservatie/" + reservatieDTO.getOrganisatie().getBedrijfsNaam() + "/step2";
    }

    @PostMapping(value = "/step2", params = "next")
    public String processWidgetStep2(@ModelAttribute Tijdslot tijdslot, BindingResult bindingResult) {
        reservatieDTO.setTijdslot(tijdslot);
        return "redirect:/reservatie/" + reservatieDTO.getOrganisatie().getBedrijfsNaam() + "/step3";
    }

    @GetMapping("/{slug}/step3")
    public String showWidgetStep3(@PathVariable String slug, Model model, Principal principal) {
        if (reservatieDTO == null || reservatieDTO.getOrganisatie().getBedrijfsNaam() == null || !reservatieDTO.getOrganisatie().getBedrijfsNaam().equals(slug)) {
            return "redirect:/reservatie/" + slug;
        }
        if(principal != null){
            reservatieDTO.setUser(userService.getUserByEmail(principal.getName()));
           return "redirect:/reservatie/" + reservatieDTO.getOrganisatie().getBedrijfsNaam() + "/step4";
        }
        model.addAttribute("user", new User());
        return "templatesReservatie/booking_step3";
    }

    @PostMapping(value = "/step3", params = "previous")
    public String processWidgetStep3Previous() {
        return "redirect:/reservatie/" + reservatieDTO.getOrganisatie().getBedrijfsNaam() + "/step2";
    }

    @PostMapping(value = "/step3", params = "next")
    public String processWidgetStep3Next(Principal principal, @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || userService.getUserByEmail(user.getEmail())!= null || organisatieService.getOrganisatieByEmail(user.getEmail())!= null) {
            return "templatesReservatie/booking_step3";
        }
        reservatieDTO.setUser(user);
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
        return "redirect:/reservatie/" + reservatieDTO.getOrganisatie().getBedrijfsNaam() + "/step4";
    }

    @GetMapping("/{slug}/step4")
    public String showWidgetStep4(@PathVariable String slug, Model model) {
        if (reservatieDTO == null || reservatieDTO.getOrganisatie().getBedrijfsNaam() == null || !reservatieDTO.getOrganisatie().getBedrijfsNaam().equals(slug)) {
            return "redirect:/reservatie/" + slug;
        }
        model.addAttribute("reservatie", reservatieDTO);
        return "templatesReservatie/booking_step4";
    }

    @PostMapping(value = "/step4", params = "previous")
    public String processWidgetStep4Previous() {
        return "redirect:/reservatie/" + reservatieDTO.getOrganisatie().getBedrijfsNaam() + "/step1";
    }

    @PostMapping(value = "/{slug}/step4", params = "bevestig")
    public String processStep4(@PathVariable String slug,Principal principal) {
        if(principal != null){
            reservatieDTO.setUser(userService.getUserByEmail(principal.getName()));
            return "redirect:/instellingen";
        }
        else{
            return "redirect:/";
        }
    }

    @GetMapping("/{slug}/getagenda")
    public String ajaxDates(@PathVariable String slug,
                            @RequestParam String date,
                            @RequestParam String medewerker,
                            @RequestParam String optie,
                            Model model) {
        return "fragmentsReservatie/hours :: hours";
    }

    @GetMapping("/{slug}/getemployees")
    public String ajaxMedewerkers(@PathVariable String slug,
                                  @RequestParam String optie,
                                  Model model) {
        reservatieDTO.setOptie(optie);
        List<Medewerker> medewerkers = optieService.getMedewerkerByOptie(optie ,organisatieService.getOrganisatieByName(slug));
        model.addAttribute("medewerkers", medewerkers);
        return "fragmentsReservatie/employees :: employees";
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
