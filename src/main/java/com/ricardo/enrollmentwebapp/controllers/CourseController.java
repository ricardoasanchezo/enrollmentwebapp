package com.ricardo.enrollmentwebapp.controllers;

import com.ricardo.enrollmentwebapp.entities.Course;
import com.ricardo.enrollmentwebapp.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/courses")
public class CourseController
{
    private final CourseService courseService;

    @PostMapping("/add")
    public void addCourse(Course course)
    {
        courseService.addCourse(course);
    }

    @GetMapping("/get")
    public Course getCourseByCode(@RequestParam String code)
    {
        return courseService.getCourseByCode(code).orElseThrow();
    }

    @GetMapping("/getAllCodes")
    public List<String> getAllCodes()
    {
        return courseService.getAllCourseCodes();
    }

    @PutMapping("/update")
    public Course updateCourseByCode(Course course)
    {
        return courseService.updateCourse(course);
    }


}
