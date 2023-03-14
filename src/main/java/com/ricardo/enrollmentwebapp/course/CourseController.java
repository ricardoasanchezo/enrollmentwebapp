package com.example.demo.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController
{
    @Autowired
    CourseService service;

    @PostMapping("/saveCourse")
    public Course saveCourse(@RequestBody Course course)
    {
        return service.saveCourse(course);
    }

    @PostMapping("/saveCourses")
    public List<Course> saveCourses(@RequestBody List<Course> courses)
    {
        return service.saveCourses(courses);
    }

    @GetMapping("/getCourses")
    public List<Course> getAllCourses()
    {
        return service.getAllCourses();
    }

    @GetMapping("/getCourse/{code}")
    public Course getCourseByCode(@PathVariable String code)
    {
        return service.getCourseByCode(code);
    }

    @DeleteMapping("/deleteCourseByCode/{code}")
    public String deleteCourseByCode(@PathVariable String code)
    {
        return service.deleteCourseByCode(code);
    }
}