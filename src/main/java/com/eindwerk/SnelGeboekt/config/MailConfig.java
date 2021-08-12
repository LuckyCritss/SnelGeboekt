package com.eindwerk.SnelGeboekt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.elasticemail.com");
        mailSender.setPort(2525);

        mailSender.setUsername("noreply@SnelGeboekt.be");
        //mailSender.setPassword("3g@NCfPg9pYgB3s");
        mailSender.setPassword("B62C1E07EB2053E73EEB9D80329731CA1CC1");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
/*
    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("User %s just created a new Jibber");
        message.setText(
                "User %s just created a new Jibber in your JIbberJabber feed:\n");
        return message;
    }
 */

}
