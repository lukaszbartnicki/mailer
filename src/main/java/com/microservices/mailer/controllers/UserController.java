package com.microservices.mailer.controllers;


import com.microservices.mailer.dto.UserModel;
import com.microservices.mailer.models.UserEntity;
import com.microservices.mailer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/AddEmail")
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel addEmail(@RequestBody UserEntity entity) throws Exception {
        return userService.createUser(entity);
    }
    @GetMapping("/GetAll")
    @ResponseStatus(HttpStatus.OK)
    public List<UserModel> getAllEmails() throws Exception {
        return userService.getAll();
    }

    @DeleteMapping("/DeleteEmail/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserModel remove(@PathVariable String email) throws Exception {
        return userService.remove(email);
    }

    @PutMapping("/ChangeEmail")
    @ResponseStatus(HttpStatus.OK)
    public UserModel changeMail(@RequestParam String email, @RequestParam String newEmail) throws Exception {
        return userService.changeEmail(email, newEmail);
    }


}
