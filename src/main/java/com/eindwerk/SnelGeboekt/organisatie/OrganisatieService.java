package com.eindwerk.SnelGeboekt.organisatie;


import com.eindwerk.SnelGeboekt.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

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
    Organisatie getOrganisatieByHandle(String voorNaam);

    @PreAuthorize("isAuthenticated()")
    Organisatie getOrganisatieComplete(String achterNaam);

    Organisatie findOrganisatie(String needle);

    List<Organisatie> getAll();

    void save(Organisatie organisatie) throws UserService.PasswordException, UserService.PasswordMisMatchException;
}
