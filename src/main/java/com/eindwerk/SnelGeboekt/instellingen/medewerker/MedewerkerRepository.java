package com.eindwerk.SnelGeboekt.instellingen.medewerker;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedewerkerRepository extends JpaRepository<Medewerker, Integer> {

    @Query("select o from Medewerker o where o.organisatie = :organisatie")
    List<Medewerker> getMedewerkersByOrganisatie(@Param("organisatie") Organisatie organisatie);

    @Query("select o.naam from Medewerker o where o.organisatie = :organisatie")
    List<String> getMedewerkersNaamByOrganisatie(@Param("organisatie") Organisatie organisatie);

    @Query("select o.id from Medewerker o where o.organisatie = :organisatie")
    List<Integer> getMedewerkersIdByOrganisatie(@Param("organisatie") Organisatie organisatie);

    @Query("select o from Medewerker o where o.id = :id")
    Medewerker getMedewerkerById(@Param("id")int id);




}
