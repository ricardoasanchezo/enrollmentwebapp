package com.ricardo.enrollmentwebapp.appuser;

import com.ricardo.enrollmentwebapp.registration.token.ConfirmationToken;
import com.ricardo.enrollmentwebapp.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AppUserService implements UserDetailsService
{
    @Autowired
    private final AppUserRepository appUserRepository;
    @Autowired
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return appUserRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username " + username + " not found!"));
    }

    /**
     * Signs up a new user to the web application. Saves username and password, and creates a token for their
     * account confirmation.
     * @param appUser The app user to sign up, received from the request made by the RegistrationService class.
     * @return The token the user needs to confirm for their account validation,
     * or a warning if the user already exists.
     */
    public String SignUpUser(AppUser appUser)
    {
        boolean userExists = appUserRepository.findByUsername(appUser.getUsername()).isPresent();
        if (userExists) return "User " + appUser.getUsername() + " already exists!";

        String encodedPassword = passwordEncoder().encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * Enables the user that matches the received username through the AppUserRepository.
     * @param username The username of the user to enable.
     */
    public int enableAppUser(String username)
    {
        return appUserRepository.enableAppUser(username);
    }
}
