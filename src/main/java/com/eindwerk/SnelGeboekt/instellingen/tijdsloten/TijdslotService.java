package com.eindwerk.SnelGeboekt.instellingen.tijdsloten;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;

import java.util.List;

public interface TijdslotService {

    List<Tijdslot> getAll();

    void saveOrUpdate(Tijdslot tijdslot);

    Tijdslot getById(int id);

    List<Tijdslot> getTijdslotenByMedewerker(Medewerker medewerker);

    List<Tijdslot> getTijdslotenByMedewerkerAndDay(Medewerker medewerker, String day);

    List<Tijdslot> getTijdslotenByMedewerkers(List<Medewerker> medewerkers);

    void delete(int id);

    void deleteTijdslotenByMedewerkerId(int medewerkerId);

}
