package com.eindwerk.SnelGeboekt.notification;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.reservatie.ReservatieDTO;
import com.eindwerk.SnelGeboekt.user.User;

public interface NotificationService {

    void sendAccountRegistrationOrganisatie(Organisatie organisatie);

    void sendAccountUpdateOrganisatie(Organisatie organisatie);

    void sendAccountRegistrationUser(User user);

    void sendAccountUpdateUser(User user);

    void sendSuccesfullReservateieUser(ReservatieDTO reservatieDTO);

    void sendSuccesfullReservateieOrganisatie(ReservatieDTO reservatieDTO);

}
