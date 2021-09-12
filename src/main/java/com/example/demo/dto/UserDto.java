package com.example.demo.dto;

import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private String email;
    private String phonenumber;
    private String name;
    private String surname;
    private Timestamp birthday;
    private Set<String> roles;

    public static UserDto fromUser(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setPhonenumber(user.getPhonenumber());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setBirthday(user.getBirthday());
        Set<String> tmp = new HashSet<>();
        for (RoleEntity role : user.getRoles()) {
            tmp.add(role.getName().toString());
        }
        userDto.setRoles(tmp);
        return userDto;
    }
}