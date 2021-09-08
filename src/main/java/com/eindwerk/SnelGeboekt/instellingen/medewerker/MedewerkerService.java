package com.eindwerk.SnelGeboekt.instellingen.medewerker;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;

import java.util.List;


public interface MedewerkerService {

    List<Medewerker> getAll();

    void saveOrUpdate(Medewerker medewerker);

    Medewerker getById(int id);

    List<Medewerker> getMedewerkerByOrganisation(Organisatie organisatie);

    public void delete(int id);
}

