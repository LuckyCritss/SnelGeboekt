package com.eindwerk.SnelGeboekt.instellingen.tijdsloten;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "tijdsloten")
public class Tijdsloten {

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

    private String start;

    private String einde;

    @ManyToOne
    @JoinColumn(name = "agenda_id")
    Agenda agenda;

    @JoinColumn(name = "organisatie_id")
    @ManyToOne
    private Organisatie organisatie;





}
