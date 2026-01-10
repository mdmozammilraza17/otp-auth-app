package com.auth.user_auth.service;

import com.auth.user_auth.entity.OtpVerification;
import com.auth.user_auth.entity.User;
import com.auth.user_auth.exception.UserAlreadyExistsException;
import com.auth.user_auth.repository.OtpRepository;
import com.auth.user_auth.repository.UserRepository;
import com.auth.user_auth.utility.OtpUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpRepository otpRepository;
    private final EmailService emailService;
    private final OtpUtil otpUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, OtpRepository otpRepository, EmailService emailService, OtpUtil otpUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpRepository = otpRepository;
        this.emailService = emailService;
        this.otpUtil = otpUtil;
    }


    // User Register
    @Transactional
    public void register (User user)
    {

        // Check username should not exist
        if (userRepository.findByUsername(user.getUsername()).isPresent())
        {
            throw new UserAlreadyExistsException("User already exists!!");
        }

        // Check email should not exist
        if (userRepository.findByEmail(user.getEmail()).isPresent())
        {
            throw new RuntimeException("This email is already register");
        }

        // Save the user with enabled = false
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user.setEnabled(false);

        // Save the user finally
        User savedUser = userRepository.save(user);

        // Generate OTP
        String otp = otpUtil.generateOtp();

        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setEmail(user.getEmail());
        otpVerification.setMobile(user.getMobile());
        otpVerification.setCreatedAt(LocalDateTime.now());
        otpVerification.setOtp(passwordEncoder.encode(otp));
        otpVerification.setExpiredAt(LocalDateTime.now().plusMinutes(5));
        otpVerification.setVerified(false);

        otpVerification.setUser(savedUser);

        otpRepository.save(otpVerification);

        emailService.sendOtp(user.getEmail(), otp);

    }

    public void verifyOtp(String email, String otp) {

        OtpVerification otpEntity =
                otpRepository.findTopByEmailAndVerifiedFalseOrderByCreatedAtDesc(email)
                        .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (!passwordEncoder.matches(otp, otpEntity.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        if (otpEntity.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        otpEntity.setVerified(true);
        otpRepository.save(otpEntity);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEnabled(true);
        userRepository.save(user);
    }

    // Spring checks that either username is in the database or not
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("user not found: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().replace("ROLE_",""))
                .disabled(!Boolean.TRUE.equals(user.getEnabled()))
                .build();
    }
}
