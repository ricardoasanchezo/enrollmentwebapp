package com.ricardo.enrollmentwebapp.security.auth.registration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegistrationRequest
{
    private final String username;
    private final String password;
}
