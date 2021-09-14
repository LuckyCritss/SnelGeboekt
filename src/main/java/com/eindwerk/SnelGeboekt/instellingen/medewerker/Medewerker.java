package com.eindwerk.SnelGeboekt.instellingen.medewerker;

import com.eindwerk.SnelGeboekt.instellingen.optie.Optie;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.Tijdslot;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name="medewerker")
public class Medewerker {

        public enum MedewerkerLijst {
                NVT("Niet van toepassing"), Med1("Medewerker 1");

                private final String naamMedewerker;

                MedewerkerLijst(String naamMedewerker) {
                        this.naamMedewerker = naamMedewerker;
                }
                
                public String getDisplayName() {
                        return naamMedewerker;
                }
        }

    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @NotNull(message = "value mismatch")
        @Transient
        private MedewerkerLijst naamMedewerker = MedewerkerLijst.NVT;

        @NotBlank(message = "{org.blank}")
        private String naam;
        
        //  @MapsId
        @JoinColumn(name = "organisatie_id")
        @ManyToOne()
        private Organisatie organisatie;

        @OneToMany(mappedBy = "medewerker" , orphanRemoval = true, cascade = CascadeType.ALL)
        private List<Tijdslot> tijdslot;

        @OneToMany(mappedBy = "medewerker" , orphanRemoval = true, cascade = CascadeType.ALL)
        private List<Optie> opties;
}
