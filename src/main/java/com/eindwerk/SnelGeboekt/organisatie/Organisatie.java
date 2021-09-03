package com.eindwerk.SnelGeboekt.organisatie;

import com.eindwerk.SnelGeboekt.instellingen.optie.Optie;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.Agenda;
import com.eindwerk.SnelGeboekt.reservatie.Reservatie;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="organisations")
public class Organisatie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{org.blank}")
    @Email(message = "{org.email}")
    @Column(name = "email")
    private String email;

    @Size(min = 5, message = "{org.wachtwoord}")
    @Column(name = "password")
    private String wachtWoord;

    @Transient
    private String checkWachtWoord;

    @NotBlank(message = "{org.blank}")
    @Size(min = 2, message = "{org.bedrijfsNaam}")
    @Column(name = "organisation")
    private String bedrijfsNaam;

    @NotBlank(message = "{org.blank}")
    @Size(min = 5, message = "{org.paginanaam}")
    @Column(name = "pageName")
    private String paginaNaam;

    @Column(name = "zipCode")
    private int postCode;

    @NotBlank(message = "{org.blank}")
    @Size(min = 2,message = "{org.gemeente}")
    @Column(name = "city")
    private String gemeente;

    @NotBlank(message = "{org.blank}")
    @Column(name = "street")
    private String straat;

    @OneToMany(mappedBy = "organisatie")
    private List<Agenda> agenda;

    @OneToMany(mappedBy = "organisatie")
    private List<Optie> opties;
}
