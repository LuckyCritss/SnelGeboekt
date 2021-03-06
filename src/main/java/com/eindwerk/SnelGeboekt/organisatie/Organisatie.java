package com.eindwerk.SnelGeboekt.organisatie;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.reservatie.ReservatieDTO;
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
    @Email(message = "{org.email-unique}")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "{org.blank}")
    @Size(min = 5, message = "{org.wachtwoord}")
    @Column(name = "password")
    private String wachtWoord;

    @Transient
    private String checkWachtWoord;

    @NotBlank(message = "{org.blank}")
    @Size(min = 2, message = "{org.bedrijfsnaam}")
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

    @OneToMany(mappedBy = "organisatie" , cascade = CascadeType.ALL)
    private List<Medewerker> medewerker;

    @OneToMany(mappedBy = "organisatie" , cascade = CascadeType.ALL)
    private List<ReservatieDTO> reservaties;

}
