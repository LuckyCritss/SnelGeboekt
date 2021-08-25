package com.eindwerk.SnelGeboekt.instellingen;

import java.util.List;

public interface OptieService {

    List<Optie> getAll();

    void saveOrUpdate(Optie optie);


    Optie getById(int id);

    void delete(int id);
}

