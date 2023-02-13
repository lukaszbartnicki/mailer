package com.microservices.mailer.services;


import com.microservices.mailer.config.OgolnyLogger;
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
    private OgolnyLogger logger;

    public UserModel createUser(UserEntity userEntity) {
        logger.log().info("Info z logu przed tworzenien usera");
        return modelMapper.toUserModel(userRepository.save(userEntity));
    }
    public List<UserModel> getAll(){
        return modelMapper.toUserModelList(userRepository.findAll());
    }
    public UserModel remove(String email){
        UserEntity user = userRepository.findByEmail(email);
        userRepository.delete(user);
        return  modelMapper.toUserModel(user);
    }
    public UserModel changeEmail(String oldEmail, String newEmail){
        UserEntity user = userRepository.findByEmail(oldEmail);
        user.setEmail(newEmail);
        userRepository.save(user);
        return modelMapper.toUserModel(user);
    }


}
