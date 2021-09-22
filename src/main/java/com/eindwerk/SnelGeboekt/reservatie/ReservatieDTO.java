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
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@Component
@Table(name = "reservaties")
public class ReservatieDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Organisatie organisatie;
    @NotNull
    private String dienst;
    @ManyToOne
    private Medewerker medewerker;
    @NotNull
    private String date;
    @NotNull
    private String time;
    @NotNull
    public String name;
    @NotNull
    public String email;
    @NotNull
    public String tel;
}
