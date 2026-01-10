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
        return ResponseEntity.ok("OTP has been sent to your email, pls check and verify for registration!!");
    }

    // User Verification using OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {

        userService.verifyOtp(email, otp);
        return ResponseEntity.ok("User registered successfully!!");
    }


    // Change Password with old password

    // Forgot Password

    // Role Based Authorization
}
