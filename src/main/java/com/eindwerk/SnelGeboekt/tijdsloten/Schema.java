package com.eindwerk.SnelGeboekt.tijdsloten;


import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "schema's")

public class Schema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String schemaNaam;

    @ManyToOne
    @JoinColumn(name = "organisatie_id")
    Organisatie organisatie;

    //@OneToMany(mappedBy = "schema")
    //private List<Tijdsloten> tijdsloten;
}
