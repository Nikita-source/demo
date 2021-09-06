package com.example.demo.repository;

import com.example.demo.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);

    Boolean existsByLogin(String login);

    Boolean existsByEmail(String email);

    Boolean existsByPhonenumber(String phonenumber);
}
