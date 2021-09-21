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
import java.util.Date;

@Entity
@Setter
@Getter
@Component
@Table(name="reservaties")
public class Reservatie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String optie;

    private int duration;

    private String day;

    private Date date;

    private String uur;

    @Transient
    @OneToOne(cascade = CascadeType.ALL)
    private Organisatie organisatie;

    @Transient
    @OneToOne(cascade = CascadeType.ALL)
    private Medewerker medewerker;

    @Transient
    @OneToOne(cascade = CascadeType.ALL)
    private Tijdslot tijdslot;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
