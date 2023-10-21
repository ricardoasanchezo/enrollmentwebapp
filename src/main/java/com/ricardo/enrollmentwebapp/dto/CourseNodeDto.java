package com.ricardo.enrollmentwebapp.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class CourseNodeDto
{
    private String courseCode;
    private List<String> hardReqsCodes;
    private List<String> softReqsCodes;
    private String specialReqs;
    private Boolean isDistCourse;
    private Integer index;
}
