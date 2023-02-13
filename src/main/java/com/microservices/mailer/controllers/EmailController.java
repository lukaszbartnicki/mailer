package com.microservices.mailer.controllers;

import com.microservices.mailer.dto.EmailBody;
import com.microservices.mailer.dto.UserModel;
import com.microservices.mailer.services.EmailService;
import com.microservices.mailer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("email")
public class EmailController {

    @Autowired
    UserService userServices;

    @Autowired
    EmailService emailService;

    @GetMapping("/sendEmails")
    public String getTest(@RequestBody EmailBody emailBody) {
        List<UserModel> users = userServices.getAll();
        Long emailsSend = emailService.sendEmailsTo(users, emailBody);
        return "Amount of emails send to users: " + emailsSend;
    }

}
