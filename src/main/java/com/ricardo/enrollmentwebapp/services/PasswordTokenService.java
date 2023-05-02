package com.ricardo.enrollmentwebapp.services;

import com.ricardo.enrollmentwebapp.entities.ConfirmationToken;
import com.ricardo.enrollmentwebapp.entities.PasswordToken;
import com.ricardo.enrollmentwebapp.repositories.PasswordTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PasswordTokenService
{
    private final PasswordTokenRepository passwordTokenRepository;

    public void savePasswordToken(PasswordToken token)
    {
        passwordTokenRepository.save(token);
    }

    public Optional<PasswordToken> getToken(String token)
    {
        return passwordTokenRepository.findByToken(token);
    }
}
