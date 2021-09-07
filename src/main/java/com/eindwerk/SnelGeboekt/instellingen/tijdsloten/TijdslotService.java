package com.eindwerk.SnelGeboekt.instellingen.tijdsloten;

import com.eindwerk.SnelGeboekt.instellingen.optie.Optie;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;

import java.util.List;

public interface TijdslotService {

    List<Tijdslot> getAll();

    void saveOrUpdate(Tijdslot tijdslot);

    Tijdslot getById(int id);

    List<Tijdslot> getTijdslotenByOrganisatie(Organisatie organisatie);

    void delete(int id);
}
