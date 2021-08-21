package com.eindwerk.SnelGeboekt.organisatie;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name="optie")
public class Optie {

    // voor de keuzemogelijkheden
    @Id
    private int id;


    private String optie;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    Organisatie organisatie;
}
