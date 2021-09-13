package com.eindwerk.SnelGeboekt.instellingen.tijdsloten;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.instellingen.optie.Optie;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;

import java.util.List;

public interface TijdslotService {

    List<Tijdslot> getAll();

    void saveOrUpdate(Tijdslot tijdslot);

    Tijdslot getById(int id);

    List<Tijdslot> getTijdslotenByMedewerker(Medewerker medewerker);

    void delete(int id);
}
