package com.ricardo.enrollmentwebapp.dto;

import com.ricardo.enrollmentwebapp.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class CourseDTO
{
    private String code;
    private String title;
    private int creditCount;
    private String departmentName;

    public static CourseDTO courseToDTO(Course course)
    {
        return new CourseDTO(
                course.getCode(),
                course.getTitle(),
                course.getCreditCount(),
                course.getDepartment().getName()
        );
    }
}
