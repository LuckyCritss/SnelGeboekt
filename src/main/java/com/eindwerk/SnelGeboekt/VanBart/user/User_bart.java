package com.eindwerk.SnelGeboekt.VanBart.user;

import com.eindwerk.SnelGeboekt.VanBart.merchant.Merchant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "snelgeboekt__users")
public class User_bart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String eMail;

    private String Password;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    Merchant merchant;



}