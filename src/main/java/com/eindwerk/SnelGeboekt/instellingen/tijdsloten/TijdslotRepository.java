package com.eindwerk.SnelGeboekt.instellingen.tijdsloten;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TijdslotRepository extends JpaRepository<Tijdslot, Integer> {


    @Query("select t from Tijdslot t where t.medewerker = :medewerker")
    List<Tijdslot> getTijdslotenByMedewerker(@Param("medewerker") Medewerker medewerker);

}
