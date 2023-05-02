package com.ricardo.enrollmentwebapp.services;

import com.ricardo.enrollmentwebapp.entities.ConfirmationToken;
import com.ricardo.enrollmentwebapp.repositories.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService
{
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token)
    {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token)
    {
        return confirmationTokenRepository.findByToken(token);
    }

    /**
     * Confirms the token received from the RegistrationService once it has been validated.
     * @param token The token to be confirmed.
     */
    public void setConfirmedAt(String token)
    {
        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
