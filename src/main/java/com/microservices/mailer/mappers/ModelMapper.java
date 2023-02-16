package com.microservices.mailer.mappers;

import com.microservices.mailer.dto.UserModel;
import com.microservices.mailer.models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelMapper {
    public static UserModel toUserModel(UserEntity userEntity){
        UserModel result = new UserModel();
        result.setEmail(userEntity.getEmail());
        return result;
    }

    public static List<UserModel> toUserModelList(List<UserEntity> all) {
        List<UserModel> emailsList = new ArrayList<>();
        for (UserEntity userEntity: all){
            emailsList.add(toUserModel(userEntity));
        }
        return emailsList;
    }
}
