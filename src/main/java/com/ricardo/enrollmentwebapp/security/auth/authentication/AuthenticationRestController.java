package com.ricardo.enrollmentwebapp.security.auth.authentication;

import com.ricardo.enrollmentwebapp.security.auth.registration.RegistrationRequest;
import com.ricardo.enrollmentwebapp.security.auth.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationRestController
{
    private final RegistrationService registrationService;

    @PostMapping("/register")
    public String register(@RequestBody RegistrationRequest request) throws Exception
    {
        return registrationService.register(request);
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request)
//    {
//        return ResponseEntity.ok(authenticationService.authenticate(request));
//    }

}
