package com.ricardo.enrollmentwebapp.dto;

import lombok.Builder;
@Builder
public record RegistrationRequest(String username, String password)
{
}
