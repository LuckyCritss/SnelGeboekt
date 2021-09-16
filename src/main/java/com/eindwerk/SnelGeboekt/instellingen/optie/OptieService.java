package com.eindwerk.SnelGeboekt.instellingen.optie;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


public interface OptieService {

    List<Optie> getAll();

    void saveOrUpdate(Optie optie);

    Optie getById(int id);

    List<Optie> getOptiesByMedewerker(Medewerker medewerker);

    List<String> getOptiesByMedewerkersId(List<Integer> medewerkersId);

    Optie getOptieByMedewerkerAndString(Medewerker medewerker, String titel);

    int GetDuurOptie(int DuurOptie);

    void delete(int id);

    void deleteOptiesByMedewerkerId(int medewerkerId);
}

