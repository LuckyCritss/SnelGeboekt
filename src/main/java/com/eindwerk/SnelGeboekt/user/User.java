package com.eindwerk.SnelGeboekt.user;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Getter
@Setter
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{user.email1}")
    @Email(message = "{user.email}")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "{user.userName}")
    @Size(min = 5, message = "{user.gebruikersNaam}")
    @Pattern(regexp  = "^[A-Za-z0-9]*$")
    private String gebruikersNaam;

    @Size(min = 5, message = "{user.passWord}")
    @Column(name = "password")
    private String wachtWoord;

    @Size(min = 5, message = "{user.heraalWachtWoord}")
    private String heraalWachtWoord;

    @Transient
    private String wachtWoordCheck;

    @Size(min = 2, message = "{user.naam}")
    private String naam;

    @Size(min = 2, message = "{user.familyNaam}")
    private String familyNaam;

    @Size(min = 10, max = 12,message = "{user.gsmNummer}")
    private String gsmNummer;

}
