package com.eindwerk.SnelGeboekt.organisatie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OrganisatieRepository extends JpaRepository<Organisatie, Integer> {

    @Query("SELECT o from Organisatie o Where o.email = :email")
    Organisatie getOrganisatieByEmail(@Param("email") String email);

    @Override
    @Query("SELECT o from Organisatie o")
    List<Organisatie> findAll();

    @Query("SELECT o from Organisatie o where o.id = :id")
    Organisatie getOrganisatieById(@Param("id") Integer integer);

    Organisatie findByBedrijfsNaam(String bedrijfsNaam);
}
