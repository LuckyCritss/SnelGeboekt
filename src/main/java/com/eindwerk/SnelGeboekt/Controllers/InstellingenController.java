package com.eindwerk.SnelGeboekt.Controllers;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/organisatie")
public class InstellingenController {

    private OrganisatieService organisatieService;

    @Autowired
    public void setOrganisatieService(OrganisatieService organisatieService){
        this.organisatieService = organisatieService;
    }

    @GetMapping("/instellingen")
    public String rootHandler(Model model) {
        model.addAttribute ("organisaties", organisatieService.getAll());
        return ("instellingen");
    }

    @GetMapping("/add")
    public String add(Model model) {
        Organisatie organisatie = organisatieService.getNewOrganisatie();
        model.addAttribute("organisatie", organisatie);
        return "fragments/keuzemogelijkheden";
    }

    @GetMapping("/keuzemogelijkheden/{id}")
    public String edit(@PathVariable int id, Model model) {
        Organisatie organisatie = organisatieService.getById(id);
        if (organisatie == null) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
        model.addAttribute("organisatie", organisatie);
        return "fragments/keuzemogelijkheden";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
