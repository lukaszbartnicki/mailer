package com.microservices.mailer.services;

import com.microservices.mailer.dto.EmailBody;
import com.microservices.mailer.dto.UserModel;
import com.microservices.mailer.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService{

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zauwazyczewszystkiewyjatki@outlook.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public Long sendEmailsTo(List<UserModel> users, EmailBody emailBody) {
        Long emailsSend = 0L;
        for(UserModel user : users){
            sendSimpleMessage(user.getEmail(), emailBody.getSubject(), emailBody.getBody());
            emailsSend++;
        }
        return emailsSend;
    }
}