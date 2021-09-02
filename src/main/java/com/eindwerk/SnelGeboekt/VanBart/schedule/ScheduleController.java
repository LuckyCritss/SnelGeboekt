package com.eindwerk.SnelGeboekt.VanBart.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/snelgeboekt/schedule")
public class ScheduleController {


    private final TimeRangeRepository timeRangeRepository;

    public ScheduleController(TimeRangeRepository timeRangeRepository) {
        this.timeRangeRepository = timeRangeRepository;
    }


    @GetMapping("/timerange")
    public String timeTange(Model model) {
        model.addAttribute("timeranges", timeRangeRepository.findAll());
        return "snelgeboekt/timerange";
    }


}
