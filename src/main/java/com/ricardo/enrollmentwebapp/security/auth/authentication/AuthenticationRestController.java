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
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) throws Exception
    {
        String response = registrationService.register(request);
        System.out.println(response);
        AuthResponse authResponse= new AuthResponse(response);
        return ResponseEntity.ok(authResponse.toJson());
    }
}
