package com.eindwerk.SnelGeboekt.instellingen.optie;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptieRepository  extends JpaRepository<Optie, Integer> {

    @Query("select o from Optie o where o.medewerker= :medewerker")
    List<Optie> getOptiesByMedewerker(@Param("medewerker") Medewerker medewerker);

    @Query("select o.titel from Optie o where o.medewerker = :medewerkers")
    List<Optie> getOptiesByMedewerkers(@Param("medewerkers") List<Medewerker>medewerkers);

    @Query("delete from Optie o where o.medewerker.id = :medewerkerId")
    void deleteOptiesByMedewerkerId(@Param("medewerkerId") int medewerkerId);

}
