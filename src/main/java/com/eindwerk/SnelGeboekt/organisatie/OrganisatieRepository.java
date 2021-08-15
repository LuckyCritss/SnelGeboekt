package com.eindwerk.SnelGeboekt.organisatie;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrganisatieRepository extends JpaRepository<Organisatie, Integer> {

    @Query("SELECT o from Organisatie o Where o.email = :email")
    public Organisatie getOrganisatieByEmail(@Param("email") String email);

    @Query("SELECT o from Organisatie o Where o.firstName = :firstName")
    public Organisatie getOrganisatieByFirstName(@Param("firstName") String firstName);

    @Query("SELECT o from Organisatie o Where o.lastName = :lastName")
    public Organisatie getOrganisatieByLastName(@Param("lastName") String lastName);

    @Override
    @Query("SELECT o from Organisatie  o")
    public List<Organisatie> findAll();

    public Organisatie getOrganisatieById(Integer integer);
}
