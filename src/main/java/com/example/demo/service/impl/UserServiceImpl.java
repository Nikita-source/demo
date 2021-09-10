package com.example.demo.service.impl;

import com.example.demo.dto.SignupRequest;
import com.example.demo.entity.Role;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity registration(SignupRequest signupRequest) throws UserAlreadyExistException {
        if (userRepository.existsByLogin(signupRequest.getLogin())) {
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с таким Email уже существует");
        }

        UserEntity user = new UserEntity(signupRequest.getLogin(), passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getEmail(), signupRequest.getPhonenumber(), signupRequest.getName(), signupRequest.getSurname(),
                signupRequest.getBirthday());

        Set<String> reqRoles = signupRequest.getRoles();
        Set<RoleEntity> roles = new HashSet<>();

        if (reqRoles == null || reqRoles.size() == 0) {
            roles.add(roleRepository.findByName(Role.ROLE_USER));
        } else {
            reqRoles.forEach(r -> {
                if ("admin".equals(r)) {
                    roles.add(roleRepository.findByName(Role.ROLE_ADMIN));
                } else {
                    roles.add(roleRepository.findByName(Role.ROLE_USER));
                }
            });
        }
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

    @Override
    public void makeUserAnAdmin(Long id) throws UserNotFoundException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id:" + id + "  не найден"));

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Role.ROLE_ADMIN));
        roles.add(roleRepository.findByName(Role.ROLE_USER));
        user.setRoles(roles);
        userRepository.save(user);
    }


}
