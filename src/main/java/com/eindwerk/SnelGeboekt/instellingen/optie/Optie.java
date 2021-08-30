package com.eindwerk.SnelGeboekt.instellingen.optie;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name="optie")
public class Optie {

    // voor de keuzemogelijkheden
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{org.blank}")
    private String title;

    private int duurOptie;


  //  @MapsId
    @JoinColumn(name = "organisatie_id")
    @ManyToOne
    private Organisatie organisatie;
}
