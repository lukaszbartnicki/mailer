package com.microservices.mailer.services;

import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import com.microservices.mailer.config.GeneralLogger;
import com.microservices.mailer.config.MailerExceptions;
import com.microservices.mailer.dto.UserModel;
import com.microservices.mailer.models.UserEntity;
import com.microservices.mailer.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @MockBean
    private GeneralLogger logger;


    private final String email1 = "example@mail.com";
    private final String email2 = "anotherexample@mail.com";
    private final String notEmail = "notAnEmail";

    @Test
    void testCreateUserWithValidEmail() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email1);

        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserModel userModel = userService.createUser(userEntity);

        assertEquals(email1, userModel.getEmail());
    }

    @Test
    public void testCreateUserWrongMail() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(notEmail);

        Exception exception = assertThrows(Exception.class, () -> {
            when(userRepository.save(userEntity)).thenReturn(userEntity);
            userService.createUser(userEntity);
        });

        assertTrue(exception.getCause() instanceof MailerExceptions.InvalidEmailException);
        assertEquals("Tried to add " + userEntity.getEmail() + " but it is not a correct email address!", exception.getCause().getMessage());
    }

    //    @Test
//    public void testCreateUserIfAlreadyExists() throws Exception {
//        UserEntity user1 = new UserEntity();
//        UserEntity user2 = new UserEntity();
//        user1.setEmail(email1);
//        Mockito.when(userRepository.save(user1)).thenReturn(user1);
//        userService.createUser(user1);
//        user2.setEmail(email1);
//        Mockito.when(userRepository.save(user2)).thenReturn(user2);
//        Exception exception = assertThrows(Exception.class, () -> {
//
//            userService.createUser(user2);
//            System.out.println(user1); //tworzy 2 takich samych userów z tym samym ID? do wyjaśnienia
//            System.out.println(user2);
//        });
//        assertTrue(exception.getCause() instanceof MailerExceptions.DuplicateMailException);
//        assertEquals("Tried add new email " + user2.getEmail() + ", but was already in the database", exception.getCause().getMessage());
//    }
    @Test
    public void testGetAll() throws Exception {
        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();
        user1.setEmail(email1);
        user2.setEmail(email2);
        when(userRepository.save(user1)).thenReturn(user1);
        when(userRepository.save(user2)).thenReturn(user2);
        UserModel model1 = userService.createUser(user1);
        UserModel model2 = userService.createUser(user2);

        List<UserModel> userModels = new ArrayList<>(Arrays.asList(model1, model2));
        List<UserEntity> userEntities = new ArrayList<>(Arrays.asList(user1, user2));
        when(userRepository.findAll()).thenReturn(userEntities);

        List<UserModel> result = userService.getAll();

        assertEquals(userModels, result);
    }
    @Test
    public void testGetAllWhenEmpty() {
        Exception exception = assertThrows(Exception.class, () -> {
            List<UserEntity> userEntities = new ArrayList<>(Collections.emptyList());
            when(userRepository.findAll()).thenReturn(userEntities);
            userService.getAll();
        });
        assertTrue(exception.getCause() instanceof MailerExceptions.EmptyListException);
        assertEquals("Tried to list all emails, but found none", exception.getCause().getMessage());
    }
//    @Test
//    public void testRemove() throws Exception {
//        UserEntity userEntity = new UserEntity(1L, email1);
//
//        when(userRepository.save(userEntity)).thenReturn(userEntity);
//        userService.createUser(userEntity);
//
//        when(userRepository.findByEmail(email1)).thenReturn(userEntity);
//        doCallRealMethod().when(userRepository).delete(userEntity);
//
//        userService.remove(email1);
//
//        assertNull(userRepository.findByEmail(email1));
//    }

}
