package com.ricardo.enrollmentwebapp.controllers;

import com.ricardo.enrollmentwebapp.entities.Student;
import com.ricardo.enrollmentwebapp.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/student")
public class StudentApiController
{
    private final StudentService studentService;
    @GetMapping("/get")
    public Student getStudent()
    {
        try
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username =  authentication.getName();
            return studentService.findStudentById(username).get();
        }
        catch (Exception ignored){}
        return null;
    }
}
