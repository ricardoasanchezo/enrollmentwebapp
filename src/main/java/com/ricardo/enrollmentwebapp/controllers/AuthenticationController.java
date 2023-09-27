package com.ricardo.enrollmentwebapp.controllers;

import com.ricardo.enrollmentwebapp.services.MyUserService;
import com.ricardo.enrollmentwebapp.dto.Json;
import com.ricardo.enrollmentwebapp.dto.RegistrationRequest;
import com.ricardo.enrollmentwebapp.dto.ResetRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController
{
    private final MyUserService myUserService;

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/register")
    public String register()
    {
        return "registration";
    }

    @PostMapping("/register")
    // @ResponseBody
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request)
    {
        try
        {
            myUserService.register(request);
            return ResponseEntity.status(HttpStatus.OK).body("Registration Successful");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration Failed");
        }
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token)
    {
        myUserService.confirmToken(token);
        return "confirm";
    }

    @GetMapping("/reset-request")
    public String resetRequest()
    {
        return "reset-request";
    }

    @PostMapping("/reset-request")
    @ResponseBody
    public ResponseEntity<String> resetRequest(@RequestParam String username)
    {
        try
        {
            myUserService.sendResetEmail(username);
            return ResponseEntity.status(HttpStatus.OK).body("Password reset email was sent");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing password reset request");
        }
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token, Model model)
    {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    @ResponseBody
    public ResponseEntity<String> resetPassword(@RequestBody ResetRequest request)
    {
        String response = myUserService.resetPassword(request.getToken(), request.getPassword());
        return ResponseEntity.ok(Json.stringify("response", response));
    }
}
