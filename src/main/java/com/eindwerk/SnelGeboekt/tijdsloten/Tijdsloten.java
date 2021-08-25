package com.eindwerk.SnelGeboekt.tijdsloten;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "tijdsloten")
public class Tijdsloten {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalTime van;

    private LocalTime tot;

    private boolean open;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    Organisatie organisatie;





}
