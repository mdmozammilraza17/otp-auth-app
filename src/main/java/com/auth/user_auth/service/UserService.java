package com.auth.user_auth.service;

import com.auth.user_auth.entity.User;
import com.auth.user_auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // User Register
    public User register (User user)
    {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
        {
            throw new RuntimeException("User already exists!!");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent())
        {
            throw new RuntimeException("This email is already register");
        }

        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user.setEnabled(true);

        return userRepository.save(user);

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
