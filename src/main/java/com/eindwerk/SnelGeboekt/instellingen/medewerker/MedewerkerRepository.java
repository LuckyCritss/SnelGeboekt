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
    List<Medewerker> getMedewerkerByOrganisatie(@Param("organisatie") Organisatie organisatie);


}
