package com.eindwerk.SnelGeboekt.instellingen.tijdsloten;

import com.eindwerk.SnelGeboekt.instellingen.optie.Optie;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;

import java.util.List;

public interface TijdslotenService {

    List<Tijdsloten> getAll();

    void saveOrUpdate(Tijdsloten tijdsloten);

    Tijdsloten getById(int id);

    List<Tijdsloten> getTijdslotenByOrganisatie(Organisatie organisatie);

    void delete(int id);
}
