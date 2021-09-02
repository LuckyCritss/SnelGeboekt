package com.eindwerk.SnelGeboekt.VanBart.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRangeRepository extends JpaRepository<TimeRange, Integer> {

}
