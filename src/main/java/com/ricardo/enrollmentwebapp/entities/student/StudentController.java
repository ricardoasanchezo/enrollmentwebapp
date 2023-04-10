package com.ricardo.enrollmentwebapp.entities.student;

import com.ricardo.enrollmentwebapp.entities.course.Course;
import com.ricardo.enrollmentwebapp.security.user.MyUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("student")
@AllArgsConstructor
public class StudentController
{
    private final StudentService studentService;

    private String getCurrentUserId()
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
        List<Course> courses = studentService.getApprovedCourses(getCurrentUserId());
        model.addAttribute("courses", courses);
        return "courses";
    }
}
