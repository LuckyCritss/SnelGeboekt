package com.eindwerk.SnelGeboekt.instellingen.kalender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface KalenderRepository extends JpaRepository<Kalender, Integer> {

    @Override
    @Query("SELECT O from Kalender O")
    public List<Kalender> findAll();

}
