package com.ricardo.enrollmentwebapp.security.auth.authentication;

import com.ricardo.enrollmentwebapp.security.auth.registration.RegistrationRequest;
import com.ricardo.enrollmentwebapp.security.config.JwtService;
import com.ricardo.enrollmentwebapp.security.token.JWToken;
import com.ricardo.enrollmentwebapp.security.token.JWTokenRepository;
import com.ricardo.enrollmentwebapp.security.token.JWTokenType;
import com.ricardo.enrollmentwebapp.security.user.MyUser;
import com.ricardo.enrollmentwebapp.security.user.MyUserRepository;
import com.ricardo.enrollmentwebapp.security.user.Role;
import com.ricardo.enrollmentwebapp.security.user.MyUserService;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService
{
    private final MyUserService myUserService;
    private final MyUserRepository myUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JWTokenRepository jwTokenRepository;
    private final AuthenticationManager authenticationManager;

    private static final String COOKIE_NAME = "jwtcookie";

    public AuthenticationResponse register(RegistrationRequest request) throws Exception
    {
        MyUser user = MyUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        myUserService.signUpUser(user);

        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

//    public AuthenticationResponse authenticate(AuthenticationRequest request)
//    {
//        authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(
//                    request.getUsername(),
//                    request.getPassword()
//            )
//        );
//
//        MyUser myUser = myUserRepository.findByUsername(request.getUsername())
//                .orElseThrow();
//
//        String token = jwtService.generateToken(myUser);
//
//        saveUserToken(myUser, token);
//
//        return AuthenticationResponse.builder().token(token).build();
//    }

    public ResponseCookie authenticate(AuthenticationRequest request)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        MyUser myUser = myUserRepository.findByUsername(request.getUsername())
                .orElseThrow();

        String token = jwtService.generateToken(myUser);

        saveUserToken(myUser, token);

        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, token)./*path("/api").*/maxAge(24 * 60 * 60).httpOnly(true).build();

        return cookie;
    }


    private void saveUserToken(MyUser myUser, String jwtToken) {
        var token = JWToken.builder()
                .user(myUser)
                .token(jwtToken)
                .tokenType(JWTokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        jwTokenRepository.save(token);
    }
}
