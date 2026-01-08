package com.auth.user_auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicAndSecureCheckController {


    @GetMapping ("/public")
    public String publicApi ()
    {
        return "This is public api";
    }

    @GetMapping ("/secure")
    public String secureApi ()
    {
        return "This is secure api";
    }

}
