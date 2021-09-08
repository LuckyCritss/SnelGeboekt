package com.eindwerk.SnelGeboekt.instellingen.medewerker;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Getter
@Setter
@Table(name="medewerker")
public class Medewerker {

    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @NotBlank(message = "{org.blank}")
        private String naam;
        
        //  @MapsId
        @JoinColumn(name = "organisatie_id")
        @ManyToOne
        private Organisatie organisatie;

}
