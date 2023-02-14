package com.microservices.mailer.services;


import com.microservices.mailer.config.GeneralLogger;
import com.microservices.mailer.dto.UserModel;
import com.microservices.mailer.mappers.ModelMapper;
import com.microservices.mailer.models.UserEntity;
import com.microservices.mailer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GeneralLogger logger;

    public UserModel createUser(UserEntity userEntity) {
        UserEntity user = userRepository.findByEmail(userEntity.getEmail());
        if (user == null) {
            logger.log().info("New email " + userEntity.getEmail() +" added to the database");
            return modelMapper.toUserModel(userRepository.save(userEntity));
        } else {
            logger.log().info("Tried add new email " + user.getEmail() + ", but was already in the database");
            return null;
        }
    }
    public List<UserModel> getAll(){

        List<UserModel> list = modelMapper.toUserModelList(userRepository.findAll());
        if (list == null){
            logger.log().info("Tried to list all emails, but found none");
        }
        else {
            logger.log().info("All emails listed");
        }
        return list;
    }
    public UserModel remove(String email){
        UserEntity user = userRepository.findByEmail(email);
        if (user == null){
            logger.log().info("Tried to delete an email " + email + ", but not found in the database");
            return null;
        }
        else{
            userRepository.delete(user);
            logger.log().info("Email " + email + " deleted");
            return  modelMapper.toUserModel(user);
        }
    }
    public UserModel changeEmail(String email, String newEmail){
        try {
            UserEntity user = userRepository.findByEmail(email);
            if (user == null) {
                logger.log().info("Tried to change an email of " + email + " but email not found!");
                return null;
            }
            else {
                user.setEmail(newEmail);
                userRepository.save(user);
                logger.log().info("Email " + email + " changed to " + newEmail + " successfully!");
                return modelMapper.toUserModel(user);
            }
        }
        catch (Exception e){
            logger.log().info("Error during changing email from " + email + " to " + newEmail + ": " + e);
            return null;
        }
    }


}
