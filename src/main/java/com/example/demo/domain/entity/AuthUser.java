/*
package com.example.demo.domain.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Getter
@ToString(of = "user")
public class AuthUser extends org.springframework.security.core.userdetails.User {
    private final UserEntity user;

    public AuthUser(@NonNull UserEntity user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        long l = user.getId();
        return (int) l;
    }
}

*/
