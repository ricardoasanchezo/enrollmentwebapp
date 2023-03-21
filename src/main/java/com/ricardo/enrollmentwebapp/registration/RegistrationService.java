package com.ricardo.enrollmentwebapp.registration;


import com.ricardo.enrollmentwebapp.appuser.AppUser;
import com.ricardo.enrollmentwebapp.appuser.AppUserRole;
import com.ricardo.enrollmentwebapp.appuser.AppUserService;
import com.ricardo.enrollmentwebapp.email.EmailService;
import com.ricardo.enrollmentwebapp.registration.token.ConfirmationToken;
import com.ricardo.enrollmentwebapp.registration.token.ConfirmationTokenService;
import com.ricardo.enrollmentwebapp.student.Student;
import com.ricardo.enrollmentwebapp.student.StudentService;
import lombok.AllArgsConstructor;
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
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

    /**
     * Receives the registration request from the RegistrationController
     * and attempts to sign up the user through the AppUserService.
     * @param request The request received from the RegistrationController.
     * @return Message of the status of the sign-up attempt.
     */
    public String register(RegistrationRequest request)
    {
        String studentId = request.getUsername();
        if (!InputValidator.matchesRegex(studentId, InputValidator.STUDENT_ID_REGEX))
            return "Student ID invalid!";


        return appUserService.signUpUser(
                new AppUser(
                        request.getUsername(),
                        request.getPassword(),
                        AppUserRole.USER,
                        false
                )
        );
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
        appUserService.enableAppUser(confirmationToken.getAppUser().getUsername());

        return "confirmed";
    }
}
