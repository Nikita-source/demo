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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    UserService userService;
    UserRepository userRepository;
    RoleRepository roleRepository;
    BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        passwordEncoder = mock(BCryptPasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void registration() throws Exception {
        SignupRequest user = new SignupRequest("test1", "test1", "test1", "test1", "test1", "test1", new Timestamp(System.currentTimeMillis()));

        Set<String> reqRoles = new HashSet<>();
        reqRoles.add("ROLE_ADMIN");
        SignupRequest admin = new SignupRequest("test2", "test2", "test2", "test2", "test2", "test2", new Timestamp(System.currentTimeMillis()), reqRoles);

        RoleEntity userRole = new RoleEntity();
        userRole.setName(Role.ROLE_USER);
        RoleEntity adminRole = new RoleEntity();
        adminRole.setName(Role.ROLE_ADMIN);
        when(roleRepository.findByName(Role.ROLE_ADMIN)).thenReturn(adminRole);
        when(roleRepository.findByName(Role.ROLE_USER)).thenReturn(userRole);

        UserEntity userEntity = new UserEntity();
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserEntity registrationUser = userService.registration(user);
        UserEntity registrationAdmin = userService.registration(admin);

        when(userRepository.existsByLogin(user.getLogin())).thenReturn(true);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        when(userRepository.existsByPhonenumber(user.getPhonenumber())).thenReturn(true);

        assertThatExceptionOfType(UserAlreadyExistException.class)
                .isThrownBy(() -> userService.registration(user));

        assertThat(registrationUser.getLogin()).isEqualTo(user.getLogin());
        assertThat(registrationAdmin.getLogin()).isEqualTo(admin.getLogin());

        Set<RoleEntity> rolesUser = new HashSet<>();
        rolesUser.add(userRole);
        Set<RoleEntity> rolesAdmin = new HashSet<>();
        rolesAdmin.add(adminRole);
        assertThat(registrationAdmin.getRoles()).isEqualTo(rolesAdmin);
        assertThat(registrationUser.getRoles()).isEqualTo(rolesUser);
    }

    @Test
    void getAll() {
        UserEntity userEntity = new UserEntity();
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        List<UserEntity> allUsers = userService.getAll();
        assertThat(allUsers.size()).isEqualTo(1);
    }

    @Test
    void getUserById() throws Exception {
        UserEntity userEntity = new UserEntity();
        Long id = 1L;
        userEntity.setId(id);
        userEntity.setLogin("test");
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.getUserById(123L));

        UserEntity userById = userService.getUserById(id);

        assertThat(userById.getId()).isEqualTo(id);
        assertThat(userById.getLogin()).isEqualTo(userEntity.getLogin());
    }

    @Test
    void getUserByLogin() throws Exception {
        UserEntity userEntity = new UserEntity();
        Long id = 1L;
        userEntity.setId(id);
        String login = "test";
        userEntity.setLogin(login);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userRepository.findByLogin(login)).thenReturn(userEntity);

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.getUserByLogin("wrong login"));

        UserEntity userByLogin = userService.getUserByLogin(login);

        assertThat(userByLogin.getId()).isEqualTo(id);
        assertThat(userByLogin.getLogin()).isEqualTo(userEntity.getLogin());
    }

    @Test
    void makeUserAnAdmin() throws Exception {
        RoleEntity userRole = new RoleEntity();
        userRole.setName(Role.ROLE_USER);
        RoleEntity adminRole = new RoleEntity();
        adminRole.setName(Role.ROLE_ADMIN);

        UserEntity user = new UserEntity();
        user.setId(1L);
        Set<RoleEntity> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);

        when(roleRepository.findByName(Role.ROLE_ADMIN)).thenReturn(adminRole);
        when(roleRepository.findByName(Role.ROLE_USER)).thenReturn(userRole);

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.makeUserAnAdmin(user.getId());

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(adminRole);
        roles.add(userRole);
        assertThat(user.getRoles()).isEqualTo(roles);
    }

    @Test
    void unmakeUserAnAdmin() throws Exception {
        RoleEntity userRole = new RoleEntity();
        userRole.setName(Role.ROLE_USER);
        RoleEntity adminRole = new RoleEntity();
        adminRole.setName(Role.ROLE_ADMIN);

        UserEntity admin = new UserEntity();
        admin.setId(1L);
        Set<RoleEntity> adminRoles = new HashSet<>();
        adminRoles.add(userRole);
        adminRoles.add(adminRole);
        admin.setRoles(adminRoles);

        when(roleRepository.findByName(Role.ROLE_USER)).thenReturn(userRole);
        when(userRepository.save(admin)).thenReturn(admin);
        when(userRepository.findById(admin.getId())).thenReturn(Optional.of(admin));

        userService.unmakeUserAnAdmin(admin.getId());

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(userRole);
        assertThat(admin.getRoles()).isEqualTo(roles);
    }

    @Test
    void deleteUser() throws Exception {
        Long id = 1L;
        String login = "test";
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setLogin(login);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findById(123L)).thenReturn(Optional.empty());

        userService.deleteUser(id);

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.deleteUser(123L));
    }
}