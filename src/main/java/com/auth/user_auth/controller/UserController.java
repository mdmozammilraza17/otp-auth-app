package com.auth.user_auth.controller;

import com.auth.user_auth.entity.User;
import com.auth.user_auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // User Registration
    @PostMapping ("/register")
    public ResponseEntity<?> userRegistration (@RequestBody User user)
    {
        userService.register(user);
        return ResponseEntity.ok("User registered successfully!!");
    }

    // User Login

    // Change Password (User Logged-in)

    // Forget Password

    // Role Based Authorization
}
