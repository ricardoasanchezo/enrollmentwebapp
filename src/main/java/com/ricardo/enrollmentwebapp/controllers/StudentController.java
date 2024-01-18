package com.ricardo.enrollmentwebapp.controllers;

import com.ricardo.enrollmentwebapp.entities.Course;
import com.ricardo.enrollmentwebapp.entities.CourseNode;
import com.ricardo.enrollmentwebapp.entities.Student;
import com.ricardo.enrollmentwebapp.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
        Student student = studentService.findStudentById(getCurrentUsername()).orElseThrow();

        List<Course> approved = student.getApprovedCourses();
        List<CourseNode> remaining = new ArrayList<>();

        for (var courseNode: student.getMajor().getCourseNodes())
        {
            if (!approved.contains(courseNode.getCourse()))
            {
                remaining.add(courseNode);
            }
        }

        List<CourseNode> available = new ArrayList<>();
        List<CourseNode> blocked = new ArrayList<>();

        for (var rem: remaining)
        {
            if (hasApprovedAllReqs(approved, rem))
                available.add(rem);
            else
                blocked.add(rem);
        }

        return "courses";
    }

    public boolean hasApprovedAllReqs(List<Course> approved, CourseNode courseNode)
    {
        for (var hardReq: courseNode.getHardReqs())
            if (!approved.contains(hardReq))
                return false;

        for (var softReq: courseNode.getSoftReqs())
            if (approved.contains(softReq))
                return true;

        return true;
    }

    @GetMapping("/progress")
    public String progress()
    {
        return "student-progress";
    }


    @GetMapping("/enrollment")
    public String enrollment(Model model)
    {
//        Student student = studentService.findStudentById(getCurrentUsername());
//        var studentDetailedCourses = studentService.getDetailedStudentCourses(student.getId());
//
//        model.addAttribute("allApprovedCourses", studentDetailedCourses.approvedCourses());
//        model.addAttribute("remainingCoursesInMajor", studentDetailedCourses.remainingCourses());
//        model.addAttribute("remainingDistributiveCourses", studentDetailedCourses.remainingDistributiveCourses());
//        model.addAttribute("approvedDistributiveCourses", studentDetailedCourses.approvedDistributiveCourses());
//
//        model.addAttribute("student", student);
//        model.addAttribute("studentID", student.getId());
//        model.addAttribute("major", student.getMajor());
//
//        return "enrollment";

        return "homepage";
    }
}
