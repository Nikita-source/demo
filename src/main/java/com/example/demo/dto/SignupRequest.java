package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    private String login;
    private String password;
    private String email;
    private String phonenumber;
    private String name;
    private String surname;
    private Timestamp birthday;
    private Set<String> roles;
}
