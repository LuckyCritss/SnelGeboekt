package com.eindwerk.SnelGeboekt.user;

import com.eindwerk.SnelGeboekt.reservatie.Reservatie;
import com.eindwerk.SnelGeboekt.reservatie.ReservatieDTO;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{user.blank}")
    @Email(message = "{user.email}")
    @Email(message = "{user.email-unique}")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "{user.blank}")
    @Size(min = 2, message = "{user.familynaam}")
    @Column(name = "family_name")
    private String familyNaam;

    @NotBlank(message = "{user.blank}")
    @Column(name = "name")
    @Size(min = 2, message = "{user.naam}")
    private String naam;


    @Column(name = "gsm_number")
    @Size(min = 10, max = 12,message = "{user.gsmnummer}")
    private String gsmNummer;

    @NotBlank(message = "{user.blank}")
    @Size(min = 5, message = "{user.wachtwoord}")
    @Column(name = "password")
    private String wachtWoord;

    @Transient
    private String checkWachtWoord;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<ReservatieDTO> reservaties;

}
