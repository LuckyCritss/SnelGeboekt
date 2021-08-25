package com.eindwerk.SnelGeboekt.instellingen.kalender;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Getter
@Setter
@Table(name="Kalender")
public class Kalender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    Calendar c = Calendar.getInstance();
}