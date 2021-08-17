package com.eindwerk.SnelGeboekt.organisatie;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name="Organisaties")
public class Organisatie {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{org.blank}")
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank(message = "{org.blank}")
    @Size(min = 5 , message = "{org.gebruikersNaam}")
    private String gebruikersNaam;

    @Size(min = 5, message = "{org.wachtwoord}")
    @Column(name = "password")
    private String wachtWoord;

    @Size(min = 5, message = "{org.wachtwoord}")
    private String herhaalWachtWoord;

    @Transient
    private String checkWachtWoord;

    @NotBlank(message = "{org.blank}")
    @Size(min = 2, message = "{org.bedrijfsNaam}")
    private String bedrijfsNaam;

    @NotBlank(message = "{org.blank}")
    //@Pattern(regexp = "^(?!-)[A-Za-z0-9-]+([\\-\\.]{1}[a-z0-9]+)*\\.[A-Za-z]{2,6}$", message = "{org.yourPageName}")
    private String paginaNaam;

    @NotBlank(message = "{org.blank}")
    private int postCode;

    @NotBlank(message = "{org.blank}")
    @Size(min = 2,message = "{org.gemeente}")
    private String gemeente;

    @NotBlank(message = "{org.blank}")
    @Email
    private String payPalEmail;

}
