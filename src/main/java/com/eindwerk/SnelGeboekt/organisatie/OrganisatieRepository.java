package com.eindwerk.SnelGeboekt.organisatie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OrganisatieRepository extends JpaRepository<Organisatie, Integer> {

    @Query("SELECT o from Organisatie o Where o.email = :email")
    public Organisatie getOrganisatieByEmail(@Param("email") String email);

    @Query("SELECT o from Organisatie o Where o.gebruikersNaam = :gebruikersNaam")
    public Organisatie getOrganisatieBygebruikersNaam(@Param("gebruikersNaam") String gebruikersNaam);

    @Override
    @Query("SELECT o from Organisatie  o")
    public List<Organisatie> findAll();

    public Organisatie getOrganisatieById(Integer integer);
}
