package com.ambulancebooking.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    public void sendWelcomeMessage(String toMail){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(toMail);
        message.setSubject("Welcome to QuickAid! Happy to Onboard You");
        message.setText("Welcome to QuickAid! We're excited to have you on board.\n" +
                "With our app, you can easily book ambulance services at your fingertips. To get started, simply complete your profile and explore our features.\n" +
                "If you have any questions, feel free to reach out at [support email/contact information].\n" +
                "Thank you for choosing us!");
        mailSender.send(message);

    }



}
