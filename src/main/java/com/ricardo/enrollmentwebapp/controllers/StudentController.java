package com.ricardo.enrollmentwebapp.controllers;

import com.ricardo.enrollmentwebapp.entities.Course;
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
    public String courses(Model model) throws Exception
    {
        String id = getCurrentUsername();

        List<Course> allApprovedCourses = studentService.getApprovedCourses(id);
        model.addAttribute("allApprovedCourses", allApprovedCourses);

        List<Course> approvedCoursesInMajor = studentService.getApprovedCoursesInMajor(id);
        model.addAttribute("approvedCoursesInMajor", approvedCoursesInMajor);

        List<Course> remainingCoursesInMajor = studentService.getRemainingCoursesInMajor(id);
        model.addAttribute("remainingCoursesInMajor", remainingCoursesInMajor);

        List<Course> remainingDistributiveCourses = studentService.getRemainingDistributiveRequirements(id);
        model.addAttribute("remainingDistributiveCourses", remainingDistributiveCourses);

        return "courses";
    }

//    @GetMapping("/courses")
//    @ResponseBody
//    public StudentCourseDetailedDTO coursesDetailed() throws Exception
//    {
//        return studentService.getDetailedStudentCourses(getCurrentUsername());
//    }
}
