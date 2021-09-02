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

    public enum Day {
        SUN("Zondag"), MON("Maandag"), TUE("Dinsdag"), WED("Woensdag"), THU("Donderdag"), FRI("Vrijdag"), SAT("Zaterdag");

        private final String displayName;

        Day(String displayName) {
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
    private Day dag = Day.SUN;

    private String start;

    private String einde;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    Organisatie organisatie;





}
