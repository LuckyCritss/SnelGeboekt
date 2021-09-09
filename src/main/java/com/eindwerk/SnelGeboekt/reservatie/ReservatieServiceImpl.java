package com.eindwerk.SnelGeboekt.reservatie;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReservatieServiceImpl implements ReservatieService {

    ReservatieService reservatieService;

    @Autowired
    public void setReservatieService(ReservatieService reservatieService) {
        this.reservatieService = reservatieService;
    }



}
