package com.ricardo.enrollmentwebapp.dto;

import com.ricardo.enrollmentwebapp.entities.Course;

import java.util.List;

public record StudentCourseDetailedDto
(
    String studentId,
    String majorName,
    List<Course> approvedCourses,
    List<Course> remainingCourses,
    List<Course> approvedDistributiveCourses,
    List<Course> remainingDistributiveCourses
){}
