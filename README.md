# otp-auth-app

1. User Registration and Verification using OTP through email
    - check user exists or not
    - check email exists or not
    - Save the user but will set the enabled = false as first
    - There is one table for OTP Verification where we will set all fields 
      with the generated encrypted OTP with OTP lifetime as 5 min with field verified = false at first.
    - We will call the sendOtp method where email OTP send logical are 
      written and send OTP to the registered email.

    - There is verifyOtp method written for the verification purpose.
    - Will check in the otp_verification table that either email and otp are 
      there or not.
    - If OTP and email are there will check it this expired or not.
    - If not expired then set verified field as true.
    - And from user table check the email is present or not.
    - If yes present then set field enabled as true.
    
