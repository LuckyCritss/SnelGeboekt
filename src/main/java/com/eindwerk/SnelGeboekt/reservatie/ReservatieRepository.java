package com.eindwerk.SnelGeboekt.reservatie;

import com.eindwerk.SnelGeboekt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservatieRepository extends JpaRepository<Reservatie, Integer> {

    @Query("SELECT r from Reservatie r Where r.user = :user")
    List<Reservatie> getReservatiesByUser(@Param("user") User user);
}
