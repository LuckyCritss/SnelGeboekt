package com.eindwerk.SnelGeboekt.instellingen.Optie;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name="optie")
public class Optie {

    // voor de keuzemogelijkheden
    @Id
    private int id;

    @NotBlank(message = "{org.blank}")
    private String optie;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    Organisatie organisatie;
}
