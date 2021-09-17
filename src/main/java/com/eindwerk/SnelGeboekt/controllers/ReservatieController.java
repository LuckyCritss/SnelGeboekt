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
import java.util.List;

@Controller
@RequestMapping("/reservatie/")
public class ReservatieController {

    private MedewerkerService medewerkerService;
    private ReservatieService reservatieService;
    private OrganisatieService organisatieService;
    private UserService userService;
    private NotificationService notificationService;
    private Reservatie reservatie;
    private AuthenticationManager authenticationManager;
    private OptieService optieService;
    private TijdslotService tijdslotService;

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
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
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
        reservatie = new Reservatie();
        reservatie.setOrganisatie(slug);
        return "redirect:/reservatie/" + slug + "/step1";
    }

    @GetMapping("/{slug}/step1")
    public String showWidgetStep1(@PathVariable String slug, Model model) {
        if (reservatie == null || reservatie.getOrganisatie() == null || !reservatie.getOrganisatie().equals(slug)) {
            return "redirect:/reservatie/" + slug;
        }
        List<String> medewerkers = medewerkerService.getMedewerkersNaamByOrganisation(organisatieService.getOrganisatieByName(slug));
        List<Integer> medewerkersId = medewerkerService.getMedewerkersIdByOrganisation(organisatieService.getOrganisatieByName(slug));
        List<String> opties = optieService.getOptiesByMedewerkersId(medewerkersId);
        model.addAttribute("slug", slug);
        model.addAttribute("medewerkers", medewerkers);
        model.addAttribute("opties", opties);
        return "templatesReservatie/booking_step1";
    }

    @PostMapping(value = "/step1", params = "cancel")
    public String processWidgetStepCancel() {
        return "redirect:/";
    }

    @PostMapping(value = "/step1", params = "next")
    public String processWidgetStep1(@ModelAttribute String optie, @ModelAttribute String medewerker) {
        reservatie.setMedewerker(medewerker);
        reservatie.setOptie(optie);
        reservatie.setDuration(optieService.getDuurOptie(medewerker, optie));
        return "redirect:/reservatie/" + reservatie.getOrganisatie() + "/step2";
    }

    @GetMapping("/{slug}/step2")
    public String showWidgetStep2(@PathVariable String slug, Model model) {
        if (reservatie == null || reservatie.getOrganisatie() == null || !reservatie.getOrganisatie().equals(slug)) {
            return "redirect:/reservatie/" + slug;
        }
       //model.addAttribute("tijdslot", tijdslot);
        return "templatesReservatie/booking_step2";
    }

    @PostMapping(value = "/step2", params = "previous")
    public String processWidgetStep2Previous() {
        return "redirect:/reservatie/" + reservatie.getOrganisatie() + "/step1";
    }

    @PostMapping(value = "/step2", params = "select")
    public String processWidgetStep2Next(@ModelAttribute Tijdslot tijdslot, @RequestParam("select") String select) {
        reservatie.setTijdslot(tijdslot);
        return "redirect:/reservatie/" + reservatie.getOrganisatie() + "/step3";
    }

    @GetMapping("/{slug}/step3")
    public String showWidgetStep3(@PathVariable String slug, Model model, Principal principal) {
        if (reservatie == null || reservatie.getOrganisatie() == null || !reservatie.getOrganisatie().equals(slug)) {
            return "redirect:/reservatie/" + slug;
        }
        if(principal != null){
            reservatie.setUser(userService.getUserByEmail(principal.getName()));
           return "redirect:/reservatie/" + reservatie.getOrganisatie() + "/step4";
        }
        model.addAttribute("user", new User());
        return "templatesReservatie/booking_step3";
    }

    @PostMapping(value = "/step3", params = "previous")
    public String processWidgetStep3Previous() {
        return "redirect:/reservatie/" + reservatie.getOrganisatie() + "/step2";
    }

    @PostMapping(value = "/step3", params = "next")
    public String processWidgetStep3Next(@ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "templatesReservatie/booking_step3";
        }
        reservatie.setUser(user);
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
        return "redirect:/reservatie/" + reservatie.getOrganisatie() + "/step4";
    }


    @GetMapping("/{slug}/step4")
    public String showWidgetStep4(@PathVariable String slug, Model model) {
        if (reservatie == null || reservatie.getOrganisatie() == null || !reservatie.getOrganisatie().equals(slug)) {
            return "redirect:/reservatie/" + slug;
        }
        model.addAttribute("reservatie", reservatie);
        return "templatesReservatie/booking_step4";
    }

    @PostMapping(value = "/{slug}/step4", params = "anuleer")
    public String processStep4Anuleer(@PathVariable String slug,Principal principal) {
            return "redirect:/reservatielijst";

    }

    @PostMapping(value = "/{slug}/step4", params = "bevestig")
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
    public String ajaxDates(@PathVariable String slug,
                            @RequestParam String date,
                            @RequestParam String medewerker,
                            @RequestParam String optie,
                            Model model) {
        return "fragmentsReservatie/hours :: hours";
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
