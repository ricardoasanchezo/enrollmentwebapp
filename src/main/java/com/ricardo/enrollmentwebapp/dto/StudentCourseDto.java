package com.ricardo.enrollmentwebapp.dto;

import com.ricardo.enrollmentwebapp.entities.CourseNode;

public record StudentCourseDto(
        CourseNode courseNode,
        StudentCourseState state
)
{

}
