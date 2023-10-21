package com.ricardo.enrollmentwebapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class MajorUpdateRequest
{
    private String code;
    private String name;
    private List<CourseNodeDto> courseNodeDtoList;
    private Integer distCredits;
    private Integer electCredits;
}
