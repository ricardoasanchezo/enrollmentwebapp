package com.ricardo.enrollmentwebapp.entities.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController
{
    @Autowired
    CourseService courseService;

    @PostMapping("/save")
    public Course saveCourse(@RequestBody Course course)
    {
        return courseService.saveCourse(course);
    }

    @PostMapping("/saveAll")
    public List<Course> saveCourses(@RequestBody List<Course> courses)
    {
        return courseService.saveCourses(courses);
    }

    @GetMapping("/getAll")
    public List<Course> getAllCourses()
    {
        return courseService.getAllCourses();
    }

    @GetMapping("/get/{code}")
    public Course getCourseByCode(@PathVariable String code)
    {
        return courseService.getCourseByCode(code);
    }

    @DeleteMapping("/delete/{code}")
    public String deleteCourseByCode(@PathVariable String code)
    {
        return courseService.deleteCourseByCode(code);
    }
}