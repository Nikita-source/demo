package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registration(UserEntity user) throws UserAlreadyExistException {
        UserEntity tmp = userRepository.findByUsername(user.getUsername());
        if (tmp != null) {
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }
        userRepository.save(user);
    }

    public User getUser(Long id) throws UserNotFoundException {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь с id:" + id + "  не найден"));
        return User.toModel(user);
    }

    public void deleteUser(Long id) throws UserNotFoundException {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь с id:" + id + "  не найден"));
        userRepository.deleteById(id);
    }
}
