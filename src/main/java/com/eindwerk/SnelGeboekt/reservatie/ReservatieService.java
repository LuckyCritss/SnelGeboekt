package com.eindwerk.SnelGeboekt.reservatie;


import com.eindwerk.SnelGeboekt.user.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


public interface ReservatieService {

 List<Reservatie> getReservatiesByUser (User user);





}
