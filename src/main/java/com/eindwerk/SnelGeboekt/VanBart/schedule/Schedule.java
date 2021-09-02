package com.eindwerk.SnelGeboekt.VanBart.schedule;

import com.eindwerk.SnelGeboekt.VanBart.merchant.Merchant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "snelgeboekt__schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String scheduleName;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    Merchant merchant;

    @OneToMany(mappedBy = "schedule")
    private List<TimeRange> timeRange;

}