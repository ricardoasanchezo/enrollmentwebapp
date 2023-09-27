package com.ricardo.enrollmentwebapp.dto;

public class Json
{
    public static String stringify(String name, String value)
    {
        return String.format(
                """
                {"%s":"%s"}""",
                name, value);
    }
}
