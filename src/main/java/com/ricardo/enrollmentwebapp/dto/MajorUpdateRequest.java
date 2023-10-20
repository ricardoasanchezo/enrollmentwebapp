package com.ricardo.enrollmentwebapp.dto;

import java.util.List;

public record MajorUpdateRequest(
        String code,
        String name,
        List<CourseNodeDto> courseNodeShellList,
        int distCredits,
        int electCredits
)
{
}
