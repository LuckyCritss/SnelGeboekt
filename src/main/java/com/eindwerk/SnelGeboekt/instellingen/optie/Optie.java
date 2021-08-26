package com.eindwerk.SnelGeboekt.instellingen.optie;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Table(name="optie")
public class Optie {

    // voor de keuzemogelijkheden
    @Id
    private int idOptie;

    @NotBlank(message = "{org.blank}")
    private String optie;

    @NotBlank(message = "{org.blank}")
    private int duurOptie;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    Organisatie organisatie;
}
