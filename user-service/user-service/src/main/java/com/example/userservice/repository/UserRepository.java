package com.example.userservice.repository;

import com.example.userservice.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByUserId(String userId);
}
