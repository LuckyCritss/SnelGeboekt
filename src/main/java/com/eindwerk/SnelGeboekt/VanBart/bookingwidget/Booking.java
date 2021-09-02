package com.eindwerk.SnelGeboekt.VanBart.bookingwidget;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Setter
@Getter
@Component
@Scope(proxyMode= ScopedProxyMode.TARGET_CLASS, value = WebApplicationContext.SCOPE_SESSION)
public class Booking {
    private String slug;
    private StepOneData stepOneData;
    private StepTwoData stepTwoData;
    private StepThreeData stepThreeData;
}