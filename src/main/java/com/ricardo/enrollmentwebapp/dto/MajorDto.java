package com.ricardo.enrollmentwebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MajorDto
{
    private String code; // Major code - year. Ex: 120-2019
    private String name;
    private List<CourseNodeDto> courseNodes;
    private Integer distCredits;
    private Integer electCredits;
}
