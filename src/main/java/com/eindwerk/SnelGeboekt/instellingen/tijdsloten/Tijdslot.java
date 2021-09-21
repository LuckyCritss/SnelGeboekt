package com.eindwerk.SnelGeboekt.instellingen.tijdsloten;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.action.internal.OrphanRemovalAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "tijdsloten")
public class Tijdslot {

    public enum Dag {
        Maandag("Maandag"), Dinsdag("Dinsdag"), Woensdag("Woensdag"), Donderdag("Donderdag"), Vrijdag("Vrijdag"), Zaterdag("Zaterdag"), Zondag("Zondag");

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
    private String dag = Dag.Maandag.getDisplayName();

    private LocalTime start;

    private LocalTime einde;

    @ManyToOne
    @JoinColumn(name = "medewerker_id")
    Medewerker medewerker;



}