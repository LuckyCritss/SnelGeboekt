package com.eindwerk.SnelGeboekt.instellingen.tijdsloten;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "tijdsloten")
public class Tijdslot {

    public enum Dag {
        ZON("Zondag"), Maa("Maandag"), Din("Dinsdag"), WOE("Woensdag"), DON("Donderdag"), VRI("Vrijdag"), ZAT("Zaterdag");

        private final String displayName;

        Dag(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "value mismatch")
    private Dag dag = Dag.ZON;

    private LocalTime start;

    private LocalTime einde;

    @ManyToOne
    @JoinColumn(name = "medewerker_id")
    Medewerker medewerker;



}