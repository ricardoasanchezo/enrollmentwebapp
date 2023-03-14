package com.example.demo.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseService
{
    @Autowired
    CourseRepository repository;

    public Course saveCourse(Course course)
    {
        return repository.save(course);
    }

    public List<Course> saveCourses(List<Course> courses)
    {
        return repository.saveAll(courses);
    }

    public List<Course> getAllCourses()
    {
        return repository.findAll();
    }

    public Course getCourseByCode(String code)
    {
        return repository.findById(code).orElse(null);
    }

    public String deleteCourseByCode(String code)
    {
        repository.deleteById(code);
        return "Course with code: " + code + " deleted!";
    }
}
