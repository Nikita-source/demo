package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    UserEntity registration(UserEntity user) throws UserAlreadyExistException;

    List<UserEntity> getAll();

    UserEntity getUserByLogin(String login) throws UserNotFoundException;

    UserEntity getUserById(Long id) throws UserNotFoundException;

    void deleteUser(Long id) throws UserNotFoundException;
}
