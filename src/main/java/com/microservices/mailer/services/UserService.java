package com.microservices.mailer.services;


import com.microservices.mailer.config.GeneralLogger;
import com.microservices.mailer.dto.UserModel;
import com.microservices.mailer.mappers.ModelMapper;
import com.microservices.mailer.models.UserEntity;
import com.microservices.mailer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GeneralLogger logger;

    String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";


    public UserModel createUser(UserEntity userEntity) throws Exception {
        try {
            if ((userEntity.getEmail().matches(regexPattern))) {
                UserEntity user = userRepository.findByEmail(userEntity.getEmail());
                if (user == null) {
                    logger.log().info("New email " + userEntity.getEmail() + " added to the database");
                    return modelMapper.toUserModel(userRepository.save(userEntity));
                } else {
                    throw new Exception("Tried add new email " + user.getEmail() + ", but was already in the database");
                }
            } else {
                throw new Exception("Tried to add " + userEntity.getEmail() + " but it is not a correct email address!");
            }
        }catch (Exception e){
            logger.log().info("Error occurred during adding email: " + e);
            throw new Exception("Error occurred during adding email: ", e);
        }
    }
    public List<UserModel> getAll() throws Exception {
        try {
            List<UserModel> list = modelMapper.toUserModelList(userRepository.findAll());
            if (list.isEmpty()) {
                throw new Exception("Tried to list all emails, but found none");
            } else {
                logger.log().info("All emails listed");
                return list;
            }
        }catch (Exception e){
            logger.log().info("Error occurred during listing emails: " + e);
            throw new Exception("Error occurred during listing emails: ", e);
        }
    }
    public UserModel remove(String email) throws Exception {
        try {
            UserEntity user = userRepository.findByEmail(email);
            if (user == null) {
                throw new Exception("Tried to delete an email " + email + ", but not found in the database");
            } else {
                userRepository.delete(user);
                logger.log().info("Email " + email + " deleted");
                return modelMapper.toUserModel(user);
            }
        }catch (Exception e){
            logger.log().info("Error occurred during deleting email: " + e);
            throw new Exception("Error occurred during deleting email: ", e);
        }
    }
    public UserModel changeEmail(String email, String newEmail) throws Exception {
        try {
            if ((newEmail.matches(regexPattern))) {
                UserEntity user = userRepository.findByEmail(email);
                if (user == null) {
                    throw new Exception("Tried to change an email of " + email + " but email not found!");
                } else {
                    user.setEmail(newEmail);
                    userRepository.save(user);
                    logger.log().info("Email " + email + " changed to " + newEmail + " successfully!");
                    return modelMapper.toUserModel(user);
                }
            } else {
                throw new Exception("Tried to change email " + email + " to " + newEmail + ", but it is not correct email address!");
            }
        }
        catch (Exception e){
            logger.log().info("Error during changing email from " + email + " to " + newEmail + ": " + e);
            throw new Exception("Error during changing email from " + email + " to " + newEmail + ": ", e);
        }
    }
}
