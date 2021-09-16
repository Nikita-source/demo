package com.example.demo.security;

import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUserDetails create(UserEntity user) {
        List<GrantedAuthority> authorities = mapToGrantedAuthorities(user.getRoles());

        return new JwtUserDetails(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getPhonenumber(),
                user.getName(),
                user.getSurname(),
                user.getBirthday(),
                authorities);
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<RoleEntity> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName().name())
                ).collect(Collectors.toList());
    }
}
