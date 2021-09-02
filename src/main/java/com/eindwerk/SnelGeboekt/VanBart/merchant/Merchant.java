package com.eindwerk.SnelGeboekt.VanBart.merchant;

import com.eindwerk.SnelGeboekt.VanBart.schedule.Schedule;
import com.eindwerk.SnelGeboekt.VanBart.user.User_bart;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "snelgeboekt__merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String Name;
    private String Address;
    private String zip;
    private String location;
    private String Vat;

    @OneToMany(mappedBy = "merchant")
    private List<User_bart> user;

    @OneToMany(mappedBy = "merchant")
    private List<Schedule> schedule;


}
