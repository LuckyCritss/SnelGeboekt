package com.eindwerk.SnelGeboekt.tijdsloten;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tijdslot")
public class TijdslotenController {

    private TijdslotenService tijdslotenService;


    public void setTijdslotenService(TijdslotenService tijdslotenService) {
        this.tijdslotenService = tijdslotenService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tijdsloten", tijdslotenService.getAll());
        return "/tijdslot";
    }

}
