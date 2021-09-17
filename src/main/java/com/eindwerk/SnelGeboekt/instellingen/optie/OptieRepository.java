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

    @Query("select o.titel from Optie o where o.medewerker.id = :medewerkersId")
    List<String> getOptiesByMedewerkersId(@Param("medewerkersId") List<Integer> medewerkersId);

    @Query("delete from Optie o where o.medewerker.id = :medewerkerId")
    void deleteOptiesByMedewerkerId(@Param("medewerkerId") int medewerkerId);

    @Query("select o from Optie o where o.medewerker = :medewerker and o.titel = :titel")
    Optie getOptieByMedewerkerAndString(@Param("medewerker") Medewerker medewerkers , @Param("titel") String titel);

    @Query("select o.duurOptie from Optie o where o.medewerker.naam = :medewerkerNaam and o.titel = :titel")
    int getDuurOptie(@Param("medewerkerNaam") String medewerkerNaam , @Param("titel") String titel);

}
