package com.eindwerk.SnelGeboekt.notification;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.reservatie.Reservatie;
import com.eindwerk.SnelGeboekt.reservatie.ReservatieDTO;
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
        sendSimpleMessage(organisatie.getEmail(), "Account registration successfully!","this is an automatic mail to Verify that you successfully created your account on www.SnelGeboekt.be" +
                " <a href=\"www.SnelGeboekt.com/reservatie/" + organisatie.getBedrijfsNaam() + "\">Reserveer hier</a>  deze link kan je op je site zetten en kunnen jullie klanten bij ons een reservatie maken voor jullie.");
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
    public void sendSuccesfullReservateie(ReservatieDTO reservatieDTO) {
        sendSimpleMessage(reservatieDTO.getEmail(),"Succesfull Reservatie",
                "Thank you for your reservatie" );
        sendSimpleMessage(reservatieDTO.getOrganisatie().getEmail(),"Succesfull Reservatie",
                reservatieDTO.getName() + " " + "heeft gereserveerd.");
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
