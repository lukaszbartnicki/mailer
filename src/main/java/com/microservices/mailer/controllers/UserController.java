package com.microservices.mailer.controllers;


import com.microservices.mailer.dto.UserModel;
import com.microservices.mailer.models.UserEntity;
import com.microservices.mailer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController()
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/AddEmail")
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel addEmail(@RequestBody UserEntity entity) throws Exception {
        try {
            return userService.createUser(entity);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem with adding an email: " ,e);
        }
    }
    @GetMapping("/GetAll")
    @ResponseStatus(HttpStatus.OK)
    public List<UserModel> getAllEmails() throws Exception {
        try {
            return userService.getAll();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem with obtaining list of emails: " ,e);
        }
    }

    @DeleteMapping("/DeleteEmail/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserModel remove(@PathVariable String email) throws ResponseStatusException {
        try {
            return userService.remove(email);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem with deleting an email: " ,e);
        }
    }

    @PutMapping("/ChangeEmail")
    @ResponseStatus(HttpStatus.OK)
    public UserModel changeMail(@RequestParam String email, @RequestParam String newEmail) throws ResponseStatusException {
        try {
            return userService.changeEmail(email, newEmail);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem with changing an email: " ,e);
        }
    }


}
