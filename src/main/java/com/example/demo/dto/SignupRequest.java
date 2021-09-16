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

    public SignupRequest() {
    }

    public SignupRequest(String login, String password, String email, String phonenumber, String name, String surname, Timestamp birthday, Set<String> roles) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.roles = roles;
    }

    public SignupRequest(String login, String password, String email, String phonenumber, String name, String surname, Timestamp birthday) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }
}
