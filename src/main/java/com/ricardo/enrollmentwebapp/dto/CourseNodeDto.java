package com.ricardo.enrollmentwebapp.dto;

import java.util.List;

public record CourseNodeDto(
        String courseCode,
        List<String> hardReqsCodes,
        List<String> softReqsCodes,
        String specialReqs,
        boolean isDistCourse,
        int index
)
{
}
