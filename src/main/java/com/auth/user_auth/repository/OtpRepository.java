package com.auth.user_auth.repository;

import com.auth.user_auth.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> findByEmailAndOtp(String email, String otp);

    Optional<OtpVerification>
    findTopByEmailAndVerifiedFalseOrderByCreatedAtDesc(String email);

}
