package com.eindwerk.SnelGeboekt.notification;


import com.eindwerk.SnelGeboekt.organisatie.Organisatie;

public interface NotificationService {

    void sendAccountRegistration(Organisatie organisatie);

    void sendAccountUpdate(Organisatie organisatie);

}
