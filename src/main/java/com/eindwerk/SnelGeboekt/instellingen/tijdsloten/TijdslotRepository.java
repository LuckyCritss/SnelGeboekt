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

    @Query("select t from Tijdslot t where t.medewerker = :medewerker and t.dag =:day")
    List<Tijdslot> getTijdslotenByMedewerkerAndDay(@Param("medewerker") Medewerker medewerker, @Param("day") String day);

    @Query("select t from Tijdslot t where t.medewerker = :medewerkers")
    List<Tijdslot> getTijdslotenByMedewerkers(@Param("medewerkers") List<Medewerker> medewerkers);

    @Query("delete from Tijdslot o where o.medewerker.id = :medewerkerId")
    void deleteTijdslotenByMedewerkerId(@Param("medewerkerId") int medewerkerId);
}
