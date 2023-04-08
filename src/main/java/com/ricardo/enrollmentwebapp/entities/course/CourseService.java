package com.ricardo.enrollmentwebapp.entities.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseService
{
    @Autowired
    CourseRepository courseRepository;

    public Course saveCourse(Course course)
    {
        return courseRepository.save(course);
    }

    public List<Course> saveCourses(List<Course> courses)
    {
        return courseRepository.saveAll(courses);
    }

    public List<Course> getAllCourses()
    {
        return courseRepository.findAll();
    }

    public Course getCourseByCode(String code)
    {
        return courseRepository.findById(code).orElse(null);
    }

    public String deleteCourseByCode(String code)
    {
        courseRepository.deleteById(code);
        return "Course with code: " + code + " deleted!";
    }
}
