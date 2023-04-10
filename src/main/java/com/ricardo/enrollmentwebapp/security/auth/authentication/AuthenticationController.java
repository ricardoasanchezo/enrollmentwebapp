package com.ricardo.enrollmentwebapp.security.auth.authentication;

import com.ricardo.enrollmentwebapp.security.auth.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController
{
    private final RegistrationService registrationService;

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/register")
    public String register()
    {
        return "register";
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token)
    {
        // return registrationService.confirmToken(token);
        registrationService.confirmToken(token);
        return "confirm";
    }
}
