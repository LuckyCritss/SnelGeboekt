package com.eindwerk.SnelGeboekt.controllers;

import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.TijdslotRepository;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import com.eindwerk.SnelGeboekt.reservatie.ReservatieDTO;
import com.eindwerk.SnelGeboekt.reservatie.ReservatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

import java.security.Principal;
import java.util.List;

@Controller
public class AgendaController {

    private OrganisatieService organisatieService;
    private ReservatieService reservatieService;

    @Autowired
    public void setOrganisatieService(OrganisatieService organisatieService){
        this.organisatieService = organisatieService;
    }
    @Autowired
    public void setReservatieService(ReservatieService reservatieService) {
        this.reservatieService = reservatieService;
    }

    @GetMapping("/instellingen/agenda")
    public String agendaHandler(Principal principal, Model model) {
        if(organisatieService.getOrganisatieByEmail(principal.getName()) != null){
            Organisatie organisatie = organisatieService.getOrganisatieByEmail(principal.getName());
            List<ReservatieDTO> reservaties = reservatieService.getReservatiesByOrganisatie(organisatie);
            model.addAttribute("reservaties", reservaties);
            return ("templatesInstellingen/kalender");
        }
        return "redirect:/instellingen";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
