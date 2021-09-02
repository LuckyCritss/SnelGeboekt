package com.eindwerk.SnelGeboekt.VanBart.schedule;

import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "snelgeboekt__timeslots")
public class TimeRange {
    public enum Day {
        SUN("Sunday"), MON("Monday"), TUE("Tuesday"), WED("Wednesday"), THU("Thursday"), FRI("Friday"), SAT("Saturday");

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
    private Day day = Day.SUN;

    private String start;

    private String end;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    Schedule schedule;


}
