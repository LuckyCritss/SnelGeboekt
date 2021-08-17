package com.eindwerk.SnelGeboekt.notification;

import com.eindwerk.SnelGeboekt.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendNotification(User user) {

       /*
       for (User follower:user.getFollowers()) {
            sendSimpleMessage(follower.getEmail(), "new Jibber in your feed","new jibber");
        }
        */
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
