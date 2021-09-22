package com.eindwerk.SnelGeboekt.reservatie;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.user.User;
import java.util.List;

public interface ReservatieService {

 List<ReservatieDTO> getReservatiesByEmail (String email);

 List<ReservatieDTO> getReservatiesByOrganisatie (Organisatie organisatie);

 ReservatieDTO getReservatieById(int id);

 void save(ReservatieDTO reservatieDTO);

 void delete(int id);

}
