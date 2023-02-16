package com.microservices.mailer.services;

import com.microservices.mailer.config.GeneralLogger;
import com.microservices.mailer.config.MailerExceptions;
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


    String regexPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public UserModel createUser(UserEntity userEntity) throws RuntimeException {
        try {
            if ((userEntity == null || userEntity.getEmail() == null || !userEntity.getEmail().matches(regexPattern))) {
                throw new MailerExceptions.InvalidEmailException("Tried to add " + userEntity.getEmail() + " but it is not a correct email address!");
            }
            if (userRepository.existsByEmail(userEntity.getEmail())) {
                throw new MailerExceptions.DuplicateMailException("Tried add new email " + userEntity.getEmail() + ", but was already in the database");
            }
            if(logger != null) {
                logger.log().info("New email " + userEntity.getEmail() + " added to the database");
            }
            assert userRepository != null;
            return ModelMapper.toUserModel(userRepository.save(userEntity));
        }catch (Exception e){
            if(logger != null) {
                logger.log().info("Error occurred during adding email: " + e);
            }
            throw new RuntimeException("Error occurred during adding email: ", e);
        }
    }
    public List<UserModel> getAll() throws RuntimeException {
        List<UserModel> list = ModelMapper.toUserModelList(userRepository.findAll());
        try {
            if (list.isEmpty()) {
                throw new MailerExceptions.EmptyListException("Tried to list all emails, but found none");
            }
            if(logger != null) {
                logger.log().info("All emails listed: " + list);
            }
            return list;
        }catch (Exception e){
            if(logger != null) {
                logger.log().info("Error occurred during listing emails: " + e);
            }
            throw new RuntimeException("Error occurred during listing emails: ", e);
        }
    }
    public UserModel remove(String email) throws RuntimeException {
        try {
            UserEntity user = userRepository.findByEmail(email);
            if (user == null) {
                throw new MailerExceptions.NothingToDeleteException("Tried to delete an email " + email + ", but not found in the database");
            } else {
                userRepository.delete(user);
                if(logger != null) {
                    logger.log().info("Email " + email + " deleted");
                }
                return ModelMapper.toUserModel(user);
            }
        }catch (Exception e){
            if(logger != null) {
                logger.log().info("Error occurred during deleting email: " + e);
            }
            throw new RuntimeException("Error occurred during deleting email: ", e);
        }
    }
    public UserModel changeEmail(String email, String newEmail) throws RuntimeException {
        try {
            if ((!newEmail.matches(regexPattern))) {
                throw new MailerExceptions.IncorrectChangeException("Tried to change email " + email + " to " + newEmail + ", but it is not correct email address!");
            }
            UserEntity user = userRepository.findByEmail(email);
            if (user == null) {
                throw new MailerExceptions.NothingToChangeException("Tried to change an email of " + email + " but email not found!");
            }
            user.setEmail(newEmail);
            userRepository.save(user);
            if(logger != null) {
                logger.log().info("Email " + email + " changed to " + newEmail + " successfully!");
            }
            return ModelMapper.toUserModel(user);
            }
        catch (Exception e){
            if(logger != null) {
                logger.log().info("Error during changing email from " + email + " to " + newEmail + ": " + e);
            }
            throw new RuntimeException("Error during changing email from " + email + " to " + newEmail + ": ", e);
        }
    }
}
