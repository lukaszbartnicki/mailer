package com.microservices.mailer.repositories;

import com.microservices.mailer.models.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    List<UserEntity> findAll();

    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);
}
