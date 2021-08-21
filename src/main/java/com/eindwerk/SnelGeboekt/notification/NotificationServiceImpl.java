package com.eindwerk.SnelGeboekt.notification;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
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
    public void sendNotification(Organisatie organisatie) {

            sendSimpleMessage(organisatie.getEmail(), "Account registration successfully!","this is an automatic mail to Verify tou that you successfully created your account on www.snelgeboekt.be");
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
