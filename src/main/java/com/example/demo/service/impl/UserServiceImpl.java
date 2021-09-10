package com.example.demo.service.impl;

import com.example.demo.entity.Role;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserEntity registration(UserEntity user) throws UserAlreadyExistException {
        UserEntity tmp = userRepository.findByLogin(user.getLogin());
        if (tmp != null) {
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }
        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity userRole = roleRepository.findByName(Role.ROLE_USER);
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public List<UserEntity> getAll() {
        List<UserEntity> result = userRepository.findAll();
        return result;
    }

    @Override
    public UserEntity getUserById(Long id) throws UserNotFoundException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id:" + id + "  не найден"));
        return user;
    }

    @Override
    public UserEntity getUserByLogin(String login) throws UserNotFoundException {
        UserEntity user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UserNotFoundException("Пользователь с логном:" + login + "  не найден");
        }
        return user;
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException("Пользователь с id:" + id + "  не найден"));
        userRepository.deleteById(id);
    }
}
