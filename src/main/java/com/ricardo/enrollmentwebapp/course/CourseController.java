package com.ricardo.enrollmentwebapp.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController
{
    @Autowired
    CourseService service;

    @PostMapping("/save")
    public Course saveCourse(@RequestBody Course course)
    {
        return service.saveCourse(course);
    }

    @PostMapping("/saveAll")
    public List<Course> saveCourses(@RequestBody List<Course> courses)
    {
        return service.saveCourses(courses);
    }

    @GetMapping("/getAll")
    public List<Course> getAllCourses()
    {
        return service.getAllCourses();
    }

    @GetMapping("/get/{code}")
    public Course getCourseByCode(@PathVariable String code)
    {
        return service.getCourseByCode(code);
    }

    @DeleteMapping("/delete/{code}")
    public String deleteCourseByCode(@PathVariable String code)
    {
        return service.deleteCourseByCode(code);
    }
}