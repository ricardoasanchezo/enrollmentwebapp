package com.ricardo.enrollmentwebapp.registration;


import com.ricardo.enrollmentwebapp.appuser.AppUser;
import com.ricardo.enrollmentwebapp.appuser.AppUserRole;
import com.ricardo.enrollmentwebapp.appuser.AppUserService;
import com.ricardo.enrollmentwebapp.registration.token.ConfirmationToken;
import com.ricardo.enrollmentwebapp.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService
{
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request)
    {
        String studentId = request.getStudentId();

        if (!InputValidator.matchesRegex(studentId, InputValidator.STUDENT_ID_REGEX))
            return "Student ID invalid!";

        return appUserService.SignUpUser(
                new AppUser(
                    request.getStudentId(),
                    request.getPassword(),
                    AppUserRole.USER,
                    false
                )
        );
    }

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
