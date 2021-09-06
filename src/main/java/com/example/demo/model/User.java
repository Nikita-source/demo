package com.example.demo.model;

import com.example.demo.domain.entity.UserEntity;

public class User {
    private Long id;
    private String login;

    public static User toModel(UserEntity entity) {
        User model = new User();
        model.setId(entity.getId());
        model.setLogin(entity.getLogin());
        return model;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
