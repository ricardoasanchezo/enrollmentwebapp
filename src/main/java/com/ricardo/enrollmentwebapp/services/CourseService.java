package com.ricardo.enrollmentwebapp.services;

import com.ricardo.enrollmentwebapp.entities.Course;
import com.ricardo.enrollmentwebapp.repositories.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService
{
    private final CourseRepository courseRepository;

    public void addCourse(Course course)
    {
        courseRepository.save(course);
    }

    public Optional<Course> getCourseByCode(String code)
    {
        return courseRepository.getCourseByCode(code);
    }

    public Course updateCourse(Course course)
    {
        if (courseRepository.getCourseByCode(course.getCode()).isEmpty())
            throw new RuntimeException("Course with code: " + course.getCode() + " not found");

        return courseRepository.save(course);
    }

    public List<Course> getCoursesByCodes(List<String> codes)
    {
        return courseRepository.findAllById(codes);
    }

    public List<String> getAllCourseCodes()
    {
        return courseRepository.findAll().stream().map(Course::getCode).toList();
    }
}
