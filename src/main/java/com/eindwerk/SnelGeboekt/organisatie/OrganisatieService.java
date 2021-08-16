package com.eindwerk.SnelGeboekt.organisatie;

import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface OrganisatieService {

    class PasswordException extends Exception {
        public PasswordException(String message) {
            super(message);
        }
    }

    class PasswordMisMatchException extends Exception {
        public PasswordMisMatchException(String message) {
            super(message);
        }
    }

    @PreAuthorize("isAuthenticated()")
    Organisatie getOrganisatieByEmail(String email);

    @PreAuthorize("isAuthenticated()")
    Organisatie getOrganisatieBygebruikersNaam(String gebruikersNaam);

    Organisatie findOrganisatie(String needle);

    List<Organisatie> getAll();

    void save(Organisatie organisatie) throws PasswordException, PasswordMisMatchException;
}
