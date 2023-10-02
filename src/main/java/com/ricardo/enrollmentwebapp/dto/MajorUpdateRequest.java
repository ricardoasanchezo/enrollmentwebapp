package com.ricardo.enrollmentwebapp.dto;

import java.util.List;

public record MajorUpdateRequest(
        String code,
        String name,
        List<CourseNodeShell> courseNodeShellList,
        int distCredits,
        int electCredits
)
{
}
