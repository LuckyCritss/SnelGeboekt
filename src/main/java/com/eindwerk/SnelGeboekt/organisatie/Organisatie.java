package com.eindwerk.SnelGeboekt.organisatie;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    @Column(name = "email")
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})(?:;[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))*$", message = "{org.email}")
    private String email;

    @NotBlank(message = "{org.blank}")
    @Size(min = 2, message = "{org.voorNaam}")
    private String voorNaam;

    @NotBlank(message = "{org.blank}")
    @Size(min = 2, message = "{org.achterNaam}")
    private String achterNaam;

    @Size(min = 5, message = "{org.wachtwoord}")
    @Column(name = "password")
    private String wachtWoord;

    @Transient
    private String checkWachtWoord;

    @NotBlank(message = "{org.blank}")
    @Size(min = 2, message = "{org.bedrijfsNaam}")
    private String bedrijfsNaam;

    @NotBlank(message = "{org.blank}")
    @Pattern(regexp = "^(?!-)[A-Za-z0-9-]+([\\-\\.]{1}[a-z0-9]+)*\\.[A-Za-z]{2,6}$", message = "{org.yourPageName}")
    private String paginaNaam;

    @NotBlank(message = "{org.blank}")
    private int postCode;

    @NotBlank(message = "{org.blank}")
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})(?:;[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))*$", message = "{org.email}")
    private String payPalEmail;

}
