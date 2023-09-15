package com.ricardo.enrollmentwebapp.controllers;

import com.ricardo.enrollmentwebapp.services.MyUserService;
import com.ricardo.enrollmentwebapp.utils.Json;
import com.ricardo.enrollmentwebapp.utils.RegistrationRequest;
import com.ricardo.enrollmentwebapp.utils.ResetRequest;
import lombok.AllArgsConstructor;
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
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) throws Exception
    {
        String response = myUserService.register(request);
        return ResponseEntity.ok(Json.stringify("response", response));
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
        String response = myUserService.sendResetEmail(username);
        return ResponseEntity.ok(Json.stringify("response", response));
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
