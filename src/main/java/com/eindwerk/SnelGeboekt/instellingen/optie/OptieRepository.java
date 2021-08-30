package com.eindwerk.SnelGeboekt.instellingen.optie;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptieRepository  extends JpaRepository<Optie, Integer> {

    @Query("select o from Optie o where o.organisatie = :organisatie")
    List<Optie> getOptieByOrganisatie(@Param("organisatie") Organisatie organisatie);

    // @Query("select o from Optie o where o.organisatie = :organisatie")
    // Optie getDuurOptie(@Param("optie") Optie optie);
}
