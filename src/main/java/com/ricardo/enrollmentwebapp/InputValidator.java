package com.ricardo.enrollmentwebapp;

public class InputValidator
{
    public static final String STUDENT_ID_REGEX = "^[a-zA-Z][0-9]{8}$";

    public static boolean matchesRegex(String input, String regex)
    {
        return input.matches(regex);
    }
}
