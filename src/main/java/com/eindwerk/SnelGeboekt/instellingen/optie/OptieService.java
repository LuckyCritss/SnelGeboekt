package com.eindwerk.SnelGeboekt.instellingen.optie;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


public interface OptieService {

    List<Optie> getAll();

    void saveOrUpdate(Optie optie);

    Optie getById(int id);

    List<Optie> getOptiesByMedewerker(Medewerker medewerker);

    List<String> getOptieTitelsByMedewerkersId(int medewerkersId);

    List<Medewerker> getMedewerkerByOptie(String optie, Organisatie organisatie);

    int getDuurOptie(String titel, String medewerkerNaam);

    void delete(int id);

    void deleteOptiesByMedewerkerId(int medewerkerId);
}

