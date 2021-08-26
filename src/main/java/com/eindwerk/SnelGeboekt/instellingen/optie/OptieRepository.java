package com.eindwerk.SnelGeboekt.instellingen.optie;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OptieRepository  extends JpaRepository<Optie, String> {

    @Query("select o from Optie o where o.organisatie = :organisatie")
    Optie getOptieByOrganisatie(@Param("organisatie") Organisatie organisatie);

}
