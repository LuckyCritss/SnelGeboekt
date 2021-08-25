package com.eindwerk.SnelGeboekt.tijdsloten;



import java.util.List;

public interface TijdslotenService {

    List<Tijdsloten> getAll();

    void saveOrUpdate(Tijdsloten tijdsloten);

    Tijdsloten getById(int id);

    void delete(int id);
}
