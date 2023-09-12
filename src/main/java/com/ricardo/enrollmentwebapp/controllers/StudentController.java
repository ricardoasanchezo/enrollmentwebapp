package com.ricardo.enrollmentwebapp.controllers;

import com.ricardo.enrollmentwebapp.dto.StudentCourseDetailedDTO;
import com.ricardo.enrollmentwebapp.entities.Course;
import com.ricardo.enrollmentwebapp.entities.Student;
import com.ricardo.enrollmentwebapp.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController
{
    private final StudentService studentService;

    private String getCurrentUsername()
    {
        try
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return authentication.getName();
        }
        catch (Exception ignored){}
        return null;
    }

    @GetMapping("/courses")
    public String courses(Model model)
    {
        Student student = studentService.findStudentById(getCurrentUsername());
        var studentDetailedCourses = studentService.getDetailedStudentCourses(student.getId());

        model.addAttribute("allApprovedCourses", studentDetailedCourses.approvedCourses());
        model.addAttribute("remainingCoursesInMajor", studentDetailedCourses.remainingCourses());
        model.addAttribute("remainingDistributiveCourses", studentDetailedCourses.remainingDistributiveCourses());
        model.addAttribute("approvedDistributiveCourses", studentDetailedCourses.approvedDistributiveCourses());

        model.addAttribute("studentID", student.getId());
        model.addAttribute("major", student.getMajor().getName());
        // model.addAttribute("totalApprovedCredits", )

        return "courses";
    }
}
