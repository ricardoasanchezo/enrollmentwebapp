package com.ricardo.enrollmentwebapp.security.auth.authentication;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthResponse
{
    String response;

    public String toJson()
    {
        return String.format("""
                {
                    "response":"%s"
                }""", response);
    }
}
