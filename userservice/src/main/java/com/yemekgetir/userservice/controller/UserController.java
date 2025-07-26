package com.yemekgetir.userservice.controller;

import com.yemekgetir.userservice.entity.User;
import com.yemekgetir.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users") // bu route'ı API Gateway yönlendirecek
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
