package com.eindwerk.SnelGeboekt.tijdsloten;

import java.time.LocalTime;

public class Tijdsblok {

    private LocalTime van;
    private LocalTime tot;
    private Integer durationblock;
    private Integer duration;
    private Integer number;



    public Tijdsblok(LocalTime van, LocalTime tot, Integer durationblock, Integer duration, Integer number) {
        this.van = van;
        this.tot = tot;
        this.durationblock = durationblock;
        this.duration = duration;
        this.number = number;
    }



    //Tijdsblok tijdsblok1 = new Tijdsblok(09:00:00);
    //Tijdsblok tijdsblok2 = new Tijdsblok(14, 18, 3,5,1);


    public LocalTime getVan() {
        return van;
    }

    public void setVan(LocalTime van) {
        this.van = van;
    }

    public LocalTime getTot() {
        return tot;
    }

    public void setTot(LocalTime tot) {
        this.tot = tot;
    }

    public Integer getDurationblock() {
        return durationblock;
    }

    public void setDurationblock(Integer durationblock) {
        this.durationblock = durationblock;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
