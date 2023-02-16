package com.microservices.mailer.mappers;

import com.microservices.mailer.dto.UserModel;
import com.microservices.mailer.models.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelMapperTest {

    @Test
    public void testToUserModel() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");

        UserModel userModel = ModelMapper.toUserModel(userEntity);

        assertEquals(userEntity.getEmail(), userModel.getEmail());
    }

    @Test
    public void testToUserModelList() {
        List<UserEntity> userEntityList = new ArrayList<>();
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("test1@example.com");
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setEmail("test2@example.com");
        userEntityList.add(userEntity1);
        userEntityList.add(userEntity2);

        List<UserModel> userModelList = ModelMapper.toUserModelList(userEntityList);

        assertEquals(userEntityList.size(), userModelList.size());
        assertEquals(userEntityList.get(0).getEmail(), userModelList.get(0).getEmail());
        assertEquals(userEntityList.get(1).getEmail(), userModelList.get(1).getEmail());
    }
}