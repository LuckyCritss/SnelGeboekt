package com.eindwerk.SnelGeboekt.notification;


import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.reservatie.Reservatie;
import com.eindwerk.SnelGeboekt.user.User;

public interface NotificationService {

    void sendAccountRegistrationOrganisatie(Organisatie organisatie);

    void sendAccountUpdateOrganisatie(Organisatie organisatie);

    void sendAccountRegistrationUser(User user);

    void sendAccountUpdateUser(User user);

    void sendSuccesfullReservateieUser(Reservatie reservatie);

    void sendSuccesfullReservateieOrganisatie(Reservatie reservatie);
}
