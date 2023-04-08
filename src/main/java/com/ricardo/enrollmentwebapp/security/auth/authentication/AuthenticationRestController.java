package com.ricardo.enrollmentwebapp.security.auth.authentication;

import com.ricardo.enrollmentwebapp.security.auth.registration.RegistrationRequest;
import com.ricardo.enrollmentwebapp.security.auth.registration.RegistrationService;
import com.ricardo.enrollmentwebapp.security.config.JwtService;
import com.ricardo.enrollmentwebapp.security.user.MyUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationRestController
{
    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest request) throws Exception
    {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token)
    {
        return registrationService.confirmToken(token);
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request)
//    {
//        return ResponseEntity.ok(authenticationService.authenticate(request));
//    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request)
//    {
//        String token = authenticationService.authenticate(request).getToken();
//        return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
//    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request)
//    {
//        ResponseCookie jwtCookie = authenticationService.authenticate(request);
//
//        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body("/homepage");
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request)
    {
        ResponseCookie jwtCookie = authenticationService.authenticate(request);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body("/homepage");
    }
}
