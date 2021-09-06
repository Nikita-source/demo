package com.example.demo.service;

import com.example.demo.domain.entity.RoleEntity;
import com.example.demo.domain.entity.UserEntity;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void registration(UserEntity user) throws UserAlreadyExistException {
        UserEntity tmp = userRepository.findByLogin(user.getLogin()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with login: " + user.getLogin()));
        if (tmp != null) {
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleRepository.getById(1L));
        user.setRoles(roles);
        //user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setActive(true);
        userRepository.save(user);
    }

    public User getUserById(Long id) throws UserNotFoundException {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь с id:" + id + "  не найден"));
        return User.toModel(user);
    }

    public UserEntity getUserByIdLogin(String login) throws UserNotFoundException {
        UserEntity user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User Not Found with login: " + login));
        if (user == null) {
            throw new UserNotFoundException("Пользователь с логном:" + login + "  не найден");
        }
        return user;
    }

    public void deleteUser(Long id) throws UserNotFoundException {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь с id:" + id + "  не найден"));
        userRepository.deleteById(id);
    }
}
