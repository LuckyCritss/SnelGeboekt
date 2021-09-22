package com.eindwerk.SnelGeboekt.reservatie;

import com.eindwerk.SnelGeboekt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservatieRepository extends JpaRepository<ReservatieDTO, Integer> {

    @Query("SELECT r from ReservatieDTO r Where r.email = :email")
    List<ReservatieDTO> getReservatiesByEmail(@Param("email") String email);
}
