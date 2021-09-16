package com.eindwerk.SnelGeboekt.instellingen.medewerker;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;

import java.util.List;


public interface MedewerkerService {

    List<Medewerker> getAll();

    void saveOrUpdate(Medewerker medewerker);

    Medewerker getById(int id);

    List<Medewerker> getMedewerkersByOrganisation(Organisatie organisatie);

    List<Integer> getMedewerkersIdByOrganisation(Organisatie organisatie);

    Medewerker getMedewerkerById(int id);

    void delete(int id);
}

