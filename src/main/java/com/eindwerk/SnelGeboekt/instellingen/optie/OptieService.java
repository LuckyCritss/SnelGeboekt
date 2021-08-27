package com.eindwerk.SnelGeboekt.instellingen.optie;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface OptieService {

    List<Optie> getAll();

    void saveOrUpdate(Optie optie);

    Optie getById(int id);

    Optie getOptieByOrganisation(Organisatie organisatie);

    int GetDuurOptie(int DuurOptie);

    void delete(int id);
}

