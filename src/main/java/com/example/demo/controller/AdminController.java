package com.example.demo.controller;

import com.example.demo.dto.MessageResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "make-admin/{id}")
    public ResponseEntity<?> setAdminRole(@PathVariable(name = "id") Long id) {
        try {
            userService.makeUserAnAdmin(id);
            return ResponseEntity.ok(new MessageResponse("New administrator CREATED"));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @PutMapping(value = "unmake-admin/{id}")
    public ResponseEntity<?> delAdminRole(@PathVariable(name = "id") Long id) {
        try {
            userService.unmakeUserAnAdmin(id);
            return ResponseEntity.ok(new MessageResponse("New administrator CREATED"));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(Long.valueOf(id));
            return ResponseEntity.ok("User with ID "+ id +" deleted");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserEntity> users = userService.getAll();
            List<UserDto> result = new ArrayList<>();
            for (UserEntity user : users) {
                UserDto userDto = UserDto.fromUser(user);
                result.add(userDto);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}