package com.auth.user_auth.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException (String message)
    {
        super(message);
    }
}
