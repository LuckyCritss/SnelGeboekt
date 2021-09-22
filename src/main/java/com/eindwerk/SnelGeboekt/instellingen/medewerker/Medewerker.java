package com.eindwerk.SnelGeboekt.instellingen.medewerker;

import com.eindwerk.SnelGeboekt.instellingen.optie.Optie;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.Tijdslot;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.reservatie.ReservatieDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Parent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


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

        @OneToMany(mappedBy = "medewerker" , cascade = CascadeType.ALL)
        private List<Tijdslot> tijdslot;

        @OneToMany(mappedBy = "medewerker" , cascade = CascadeType.ALL)
        private List<Optie> opties;

        @OneToMany(mappedBy = "medewerker" , cascade = CascadeType.ALL)
        private List<ReservatieDTO> reservaties;



}
