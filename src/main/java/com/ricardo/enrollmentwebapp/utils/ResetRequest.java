package com.ricardo.enrollmentwebapp.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResetRequest
{
    private final String token;
    private final String password;

}
