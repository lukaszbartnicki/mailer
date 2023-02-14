package com.microservices.mailer.services;

import com.microservices.mailer.config.GeneralLogger;
import com.microservices.mailer.dto.EmailBody;
import com.microservices.mailer.dto.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmailService{

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private GeneralLogger logger;

    @Autowired
    UserService userServices;


    public void sendSimpleMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zauwazyczewszystkiewyjatki@outlook.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public Long sendEmailsTo(List<UserModel> users, EmailBody emailBody) throws Exception {
        try {
            Long emailsSend = 0L;
            if (users.isEmpty()) {
                throw new Exception("Tried to send emails, but no emails found in database!");
            }
            else {
                for (UserModel user : users) {
                    sendSimpleMessage(user.getEmail(), emailBody.getSubject(), emailBody.getBody());
                    emailsSend++;
                }
                logger.log().info(emailsSend + " emails sent with subject: \r\n" + emailBody.getSubject() + "\r\nand message: \r\n" + emailBody.getBody());
                return emailsSend;
            }
        }catch (Exception e){
            logger.log().info("Error occurred during sending emails: " + e);
            throw new Exception("Error occurred during sending emails", e);
        }

    }
}