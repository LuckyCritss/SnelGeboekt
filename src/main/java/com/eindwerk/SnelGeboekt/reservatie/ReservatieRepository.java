package com.eindwerk.SnelGeboekt.reservatie;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservatieRepository extends JpaRepository<ReservatieDTO, Integer> {

    @Query("SELECT r from ReservatieDTO r Where r.email = :email order by r.date asc ")
    List<ReservatieDTO> getReservatiesByEmail(@Param("email") String email);

    @Query("SELECT r from ReservatieDTO r Where r.organisatie = :organisatie order by r.date asc ")
    List<ReservatieDTO> getReservatiesByOrganisatie(@Param("organisatie") Organisatie orgnisatie);

    @Query("select o from ReservatieDTO o where o.id = :id")
    ReservatieDTO getReservatieById(@Param("id")int id);
}
