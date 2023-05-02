package com.ricardo.enrollmentwebapp.utils;

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
