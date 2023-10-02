package com.ricardo.enrollmentwebapp.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController
{
    @GetMapping("")
    @PreAuthorize("hasAuthority('admin:read')")
    public String admin()
    {
        return "admin";
    }

    @GetMapping("/majors")
    @PreAuthorize("hasAuthority('admin:read')")
    public String majors()
    {
        return "admin-majors";
    }

    @GetMapping("/majors/{code}")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getMajor(@PathVariable String code)
    {
        return "major-builder";
    }

    @GetMapping("/major-builder")
    @PreAuthorize("hasAuthority('admin:read')")
    public String majorBuilder()
    {
        return "major-builder";
    }
}
