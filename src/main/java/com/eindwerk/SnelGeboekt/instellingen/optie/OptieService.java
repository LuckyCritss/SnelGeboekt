package com.eindwerk.SnelGeboekt.instellingen.optie;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


public interface OptieService {

    List<Optie> getAll();

    void saveOrUpdate(Optie optie);

    Optie getById(int id);

    List<Optie> getOptiesByOrganisation(Organisatie organisatie);

    int GetDuurOptie(int DuurOptie);

    public void delete(int id);
}

