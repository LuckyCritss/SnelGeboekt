package com.eindwerk.SnelGeboekt.reservatie;


import org.springframework.security.access.prepost.PreAuthorize;


@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface ReservatieService {




}
