package com.ricardo.enrollmentwebapp.security.auth.registration;

import com.ricardo.enrollmentwebapp.InputValidator;
import com.ricardo.enrollmentwebapp.security.auth.confirmationtoken.ConfirmationToken;
import com.ricardo.enrollmentwebapp.security.auth.confirmationtoken.ConfirmationTokenService;
import com.ricardo.enrollmentwebapp.security.user.MyUser;
import com.ricardo.enrollmentwebapp.security.user.Role;
import com.ricardo.enrollmentwebapp.security.user.MyUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Handles all the user registration logic. The registration controller handles all client requests
 * for user registration since the AppUser package does not contain a controller.
 */
@Service
@AllArgsConstructor
public class RegistrationService
{
    private final MyUserService myUserService;
    private final ConfirmationTokenService confirmationTokenService;

    /**
     * Receives the registration request from the RegistrationController
     * and attempts to sign up the user through the AppUserService.
     * @param request The request received from the RegistrationController.
     * @return Message of the status of the sign-up attempt.
     */
    public String register(RegistrationRequest request) throws Exception
    {
        String studentId = request.getUsername();
        if (!InputValidator.matchesRegex(studentId, InputValidator.STUDENT_ID_REGEX))
            return "Student ID invalid!";


        return myUserService.signUpUser(request);
    }

    /**
     * Receives token to confirm from the Registration Controller, checks whether it exists in the database,
     * and checks the expiration time. If the token is valid, it gets confirmed and the user gets enabled.
     * @param token Token to confirm, received by the RegistrationController.
     * @return The message of the status of the confirmation attempt.
     */
    public String confirmToken(String token)
    {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null)
        {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now()))
        {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        myUserService.enableAppUser(confirmationToken.getMyUser().getUsername());

        return "confirmed";
    }

}