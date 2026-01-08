package com.auth.user_auth.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.trial_number}")
    private String fromNumber;

    public void sendOtp(String mobileNumber, String otp) {

        Message.creator(
                new PhoneNumber("+91"+mobileNumber),
                new PhoneNumber(fromNumber),
                "Your OTP is: " + otp + " (Valid for 5 minutes)"
        ).create();
    }
}
