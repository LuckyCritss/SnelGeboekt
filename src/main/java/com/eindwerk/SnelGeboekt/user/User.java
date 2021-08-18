package com.eindwerk.SnelGeboekt.user;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.*;

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
    @Column(name = "email")
    private String email;

    @NotBlank(message = "{user.blank}")
    @Size(min = 5, message = "{user.wachtWoord}")
    @Column(name = "password")
    private String wachtWoord;

    @Transient
    private String wachtWoordCheck;

    @Column(name = "name")
    @Size(min = 2, message = "{user.naam}")
    private String naam;

    @Size(min = 2, message = "{user.familyNaam}")
    @Column(name = "familyName")
    private String familyNaam;

    @Column(name = "phoneNumber")
    @Size(min = 10, max = 12,message = "{user.gsmNummer}")
    private String gsmNummer;

}
