package com.eindwerk.SnelGeboekt.instellingen.tijdsloten;

import com.eindwerk.SnelGeboekt.instellingen.optie.Optie;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TijdslotenRepository extends JpaRepository<Tijdsloten, Integer> {

    Tijdsloten getTijdslotenById(Integer integer);

    @Query("select t from Tijdsloten t where t.organisatie = :organisatie")
    List<Tijdsloten> getTijdslotenByOrganisatie(@Param("organisatie") Organisatie organisatie);

}
