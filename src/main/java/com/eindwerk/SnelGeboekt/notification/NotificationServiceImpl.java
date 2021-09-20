package com.eindwerk.SnelGeboekt.notification;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.reservatie.Reservatie;
import com.eindwerk.SnelGeboekt.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private JavaMailSender emailSender;

    @Autowired
    public void setEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendAccountRegistrationOrganisatie(Organisatie organisatie) {
        sendSimpleMessage(organisatie.getEmail(), "Account registration successfully!","this is an automatic mail to Verify that you successfully created your account on www.SnelGeboekt.be");
    }

    @Override
    public void sendAccountUpdateOrganisatie(Organisatie organisatie) {
        sendSimpleMessage(organisatie.getEmail(), "Account Update successfully!","this is an automatic mail to Verify that you successfully updated your account details");
    }

    @Override
    public void sendAccountRegistrationUser(User user) {
        sendSimpleMessage(user.getEmail(), "Account registration successfully!","this is an automatic mail to Verify that you successfully created your account on www.SnelGeboekt.be");
    }

    @Override
    public void sendAccountUpdateUser(User user) {
        sendSimpleMessage(user.getEmail(), "Account Update successfully!","this is an automatic mail to Verify that you successfully updated your account details");
    }

    @Override
    public void sendSuccesfullReservateieUser(Reservatie reservatie) {
        sendSimpleMessage(reservatie.getUser().getEmail(),"Succesfull Reservatie",
                "Thank you for your reservatie" );
    }

    @Override
    public void sendSuccesfullReservateieOrganisatie(Reservatie reservatie) {
        sendSimpleMessage(reservatie.getOrganisatie().getEmail(),"Succesfull Reservatie",
                  reservatie.getUser().getNaam() + " " + reservatie.getUser().getFamilyNaam() + "heeft gereserveerd.");
    }


    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@SnelGeboekt.be");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
