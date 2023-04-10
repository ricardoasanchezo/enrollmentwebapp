package com.ricardo.enrollmentwebapp.webcontrollers;

import com.ricardo.enrollmentwebapp.security.user.MyUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController
{
    private MyUser getCurrentUser()
    {
        try
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (MyUser) authentication.getPrincipal();
        }
        catch (Exception ignored){}
        return null;
    }

    @GetMapping("/homepage")
    public String homepage()
    {
        return "homepage";
    }

    @GetMapping("/")
    public String nothing()
    {
        return homepage();
    }

    @GetMapping("/usertest")
    public String usertest(Model model)
    {
        return "usertest";
    }
}
