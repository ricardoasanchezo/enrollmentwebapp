package com.ricardo.enrollmentwebapp.utils;

public class InputValidator
{
    // public static final String STUDENT_ID_REGEX = "^[a-zA-Z][0-9]{8}$";
    public static final String STUDENT_ID_REGEX = "\\b(?:[A-Za-z]\\d{8}|[A-Za-z]{2}\\d{7})\\b";
    public static final String PASSWORD_REGEX = "^.{8,20}$";

    public static boolean matchesRegex(String input, String regex)
    {
        return input.matches(regex);
    }

    public static boolean isStudentIdValid(String id)
    {
        return matchesRegex(id, STUDENT_ID_REGEX);
    }

    public static boolean isPasswordValid(String id)
    {
        return matchesRegex(id, PASSWORD_REGEX);
    }
}
