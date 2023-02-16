package com.microservices.mailer.repositories;

import com.microservices.mailer.models.UserEntity;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final String mail1 = "ab@wp.pl";
    private final String mail2 = "dc@wp.pl";

    @DisplayName("JUnit test for find all emails operation in UserRepository")
    @Test
    void findAll() {
        UserEntity user1 = new UserEntity(1L,mail1);
        UserEntity user2 = new UserEntity(2L,mail2);

        List<UserEntity> userEntityList = userRepository.findAll();
        assertThat(userEntityList).isNotNull();

        userRepository.save(user1);

        userEntityList = userRepository.findAll();

        assertThat(userEntityList).isNotNull();
        assertThat(userEntityList.size()).isEqualTo(1);

        userRepository.save(user2);

        userEntityList = userRepository.findAll();

        assertThat(userEntityList).isNotNull();
        assertThat(userEntityList.size()).isEqualTo(2);

        assertTrue(userEntityList.contains(user1)&&userEntityList.contains(user2));
    }
    @DisplayName("JUnit test for find by email operation in UserRepository")
    @Test
    void findByEmail() {
        UserEntity user1 = new UserEntity(1L,mail1);
        UserEntity user2 = new UserEntity(2L,mail2);

        assertNull(userRepository.findByEmail(mail1));
        assertNull(userRepository.findByEmail(mail2));

        userRepository.save(user1);

        assertThat(user1).isNotNull();

        userRepository.save(user2);

        assertThat(user2).isNotNull();

        UserEntity userEntity1 = userRepository.findByEmail(mail1);

        assertThat(userEntity1).isNotNull();

        UserEntity userEntity2 = userRepository.findByEmail(user2.getEmail());

        assertThat(userEntity2).isNotNull();

        assertSame(mail1, userEntity1.getEmail());
        assertSame(mail2, userEntity2.getEmail());
    }
    @DisplayName("JUnit test for checking if email exist operation")
    @Test
    void existsByEmail() {
        UserEntity user1 = new UserEntity(1L,mail1);
        UserEntity user2 = new UserEntity(1L,mail2);

        boolean isUser1BeforeSave = userRepository.existsByEmail(user1.getEmail());
        boolean isUser2BeforeSave = userRepository.existsByEmail(user2.getEmail());

        assertFalse(isUser1BeforeSave);
        assertFalse(isUser2BeforeSave);

        userRepository.save(user1);
        userRepository.save(user2);

        boolean isUser1AfterSave = userRepository.existsByEmail(user1.getEmail());
        boolean isUser2AfterSave = userRepository.existsByEmail(user2.getEmail());

        assertTrue(isUser1AfterSave);
        assertTrue(isUser2AfterSave);

    }
}