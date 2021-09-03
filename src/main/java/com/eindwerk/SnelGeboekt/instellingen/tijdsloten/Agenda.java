package com.eindwerk.SnelGeboekt.instellingen.tijdsloten;


import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String agendaNaam;

    @ManyToOne
    @JoinColumn(name = "organisatie_id")
    Organisatie organisatie;

    @OneToMany(mappedBy = "agenda")
    private List<Tijdsloten> tijdsloten;


}
