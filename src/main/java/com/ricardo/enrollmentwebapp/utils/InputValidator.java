package com.ricardo.enrollmentwebapp.utils;

import com.ricardo.enrollmentwebapp.dto.MajorCreationRequest;
import com.ricardo.enrollmentwebapp.dto.RegistrationRequest;

public class InputValidator
{
    public static final String STUDENT_ID_REGEX = "\\b(?:[A-Za-z]\\d{8}|[A-Za-z]{2}\\d{7})\\b";
    public static final String PASSWORD_REGEX = "^.{1,30}$";
    // public static final String YEAR_REGEX = "^20[0-9]{2}$";

    public static boolean isValidRegistrationRequest(RegistrationRequest request)
    {
        return request.username().matches(STUDENT_ID_REGEX) && request.password().matches(PASSWORD_REGEX);
    }

    public static boolean isValidMajorCreationRequest(MajorCreationRequest request)
    {
        return !request.getCode().isBlank() &&
                request.getYear() > 1900 &&
                request.getYear() < 2500 &&
               !request.getName().isBlank();
    }
}
