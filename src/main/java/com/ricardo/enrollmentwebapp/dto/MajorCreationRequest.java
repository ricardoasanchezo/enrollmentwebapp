package com.ricardo.enrollmentwebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MajorCreationRequest
{
    private String code;
    private int year;
    private String name;
    private int distCredits;
    private int electCredits;
}
