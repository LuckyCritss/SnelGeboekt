package com.eindwerk.SnelGeboekt.reservatie;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "reservaties")
public class ReservatieDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Organisatie organisatie;
    private String dienst;
    @ManyToOne(cascade = CascadeType.ALL)
    private Medewerker medewerker;
    private String date;
    private String time;
    public String name;
    public String email;
    public String tel;
}
