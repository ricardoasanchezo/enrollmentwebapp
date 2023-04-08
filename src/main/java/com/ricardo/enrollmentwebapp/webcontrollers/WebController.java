package com.ricardo.enrollmentwebapp.webcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController
{
    @GetMapping("/homepage")
    public String homepage()
    {
        return "homepage";
    }
}
