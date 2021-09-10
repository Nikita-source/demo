package com.example.demo.dto;

import com.example.demo.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Timestamp;

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

    public UserEntity toUser() {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhonenumber(phonenumber);
        user.setName(name);
        user.setSurname(surname);
        user.setBirthday(birthday);
        return user;
    }

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
        return userDto;
    }
}