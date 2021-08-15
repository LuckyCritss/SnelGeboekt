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
    @Size(min = 2, message = "{org.firstName}")
    private String firstName;

    @NotBlank(message = "{org.blank}")
    @Size(min = 2, message = "{org.lastName}")
    private String lastName;

    @Size(min = 5, message = "{org.passWord}")
    @Column(name = "password")
    private String password;

    @Transient
    private String checkPassWord;

    @NotBlank(message = "{org.blank}")
    @Size(min = 2, message = "{org.companyName}")
    private String company;

    @NotBlank(message = "{org.blank}")
    @Pattern(regexp = "^(?!-)[A-Za-z0-9-]+([\\-\\.]{1}[a-z0-9]+)*\\.[A-Za-z]{2,6}$", message = "{org.yourPageName}")
    private String yourPageName;

    @NotBlank(message = "{org.blank}")
    private int zipCode;

    @NotBlank(message = "{org.blank}")
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})(?:;[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))*$", message = "{org.email}")
    private String payPalEmail;

}
