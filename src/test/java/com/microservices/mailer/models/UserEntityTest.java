package com.microservices.mailer.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserEntityTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testSaveAndFind() {
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        entityManager.persist(user);

        UserEntity savedUser = entityManager.find(UserEntity.class, user.getId());
        assertNotNull(savedUser);
        assertEquals(user.getEmail(), savedUser.getEmail());
    }
}