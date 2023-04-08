package com.ricardo.enrollmentwebapp.security.auth.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController
{
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }
}
