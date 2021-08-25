package com.eindwerk.SnelGeboekt.instellingen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptieRepository  extends JpaRepository<Optie, String> {
}
