package com.ricardo.enrollmentwebapp.entities.student;

import com.ricardo.enrollmentwebapp.entities.course.Course;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController
{
    private final StudentService studentService;

    @GetMapping("/getApprovedCourses")
    public List<Course> getApprovedCourses(@RequestParam("studentId") String studentId) throws Exception
    {
        return studentService.getApprovedCourses(studentId);
    }

    @GetMapping("/getApprovedCoursesInMajor")
    public List<Course> getApprovedCoursesInMajor(@RequestParam("studentId") String studentId) throws Exception
    {
        return studentService.getApprovedCoursesInMajor(studentId);
    }
}
