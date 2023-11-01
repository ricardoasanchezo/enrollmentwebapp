package com.ricardo.enrollmentwebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CourseNodeDto
{
    private String code;
    private Character passingGrade;
    private List<String> hardReqs;
    private List<String> softReqs;
    private String specialReqs;
    private Boolean isDistCourse;
    private Integer nodeIndex;
}
