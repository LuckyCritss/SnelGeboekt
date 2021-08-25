package com.eindwerk.SnelGeboekt.instellingen;

import com.eindwerk.SnelGeboekt.instellingen.Optie;
import com.eindwerk.SnelGeboekt.instellingen.OptieService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


@Controller
@RequestMapping("/keuzemogelijkheden")
public class OptieController {

    private OptieService optieService;


    public void setOptieService(OptieService optieService) {
        this.optieService = optieService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("opties", optieService.getAll());
        return "/lijstkeuzemogelijkheden";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("optie", new Optie());
        return "/fragments/Keuzemogelijkheden";
    }

    @PostMapping("/edit")
    public String processForm(@Valid @ModelAttribute Optie optie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "keuzemogelijheden";
        }
        optieService.saveOrUpdate(optie);
        return "redirect:/keuzemogelijheden";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Optie optie = optieService.getById(id);
        if (optie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        model.addAttribute("optie", optie);
        return "fragments/keuzemogelijkheden";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        Optie optie = optieService.getById(id);
        if (optie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found on server");
        }
        optieService.delete(id);
        return "redirect:/keuzemogelijkheden";
    }

}
