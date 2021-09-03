package com.eindwerk.SnelGeboekt.reservatie;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class StepThreeData {

    private String email;
    private String familyNaam;
    private String naam;
    private String gsmNummer;
}
