package com.eindwerk.SnelGeboekt.reservatie;

import com.eindwerk.SnelGeboekt.user.User;
import java.util.List;

public interface ReservatieService {

 List<ReservatieDTO> getReservatiesByEmail (String email);

 void save(ReservatieDTO reservatieDTO);



}
