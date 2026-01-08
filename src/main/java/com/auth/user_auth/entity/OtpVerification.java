package com.auth.user_auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OtpVerification {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String mobile;

    private String otp;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    private boolean verified;


    @OneToOne (optional = false)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;
}
