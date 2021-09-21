package com.eindwerk.SnelGeboekt.reservatie;


import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.Tijdslot;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.*;


@Setter
@Getter
@Component
@Scope(proxyMode= ScopedProxyMode.TARGET_CLASS, value = WebApplicationContext.SCOPE_SESSION)
public class ReservatieDTO {

    private String optie;

    private int duration;

    private String date;

    private String uur;

    private Organisatie organisatie;

    private Medewerker medewerker;

    private Tijdslot tijdslot;

    private User user;
}
