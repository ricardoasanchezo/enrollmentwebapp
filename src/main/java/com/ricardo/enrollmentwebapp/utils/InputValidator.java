package com.ricardo.enrollmentwebapp.utils;

import com.ricardo.enrollmentwebapp.dto.RegistrationRequest;

public class InputValidator
{
    public static final String STUDENT_ID_REGEX = "\\b(?:[A-Za-z]\\d{8}|[A-Za-z]{2}\\d{7})\\b";
    public static final String PASSWORD_REGEX = "^.{1,30}$";

    public static boolean isValidRegistrationRequest(RegistrationRequest request)
    {
        return request.username().matches(STUDENT_ID_REGEX) && request.password().matches(PASSWORD_REGEX);
    }
}
