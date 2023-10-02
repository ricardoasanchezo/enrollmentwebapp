package com.ricardo.enrollmentwebapp.controllers;

import com.ricardo.enrollmentwebapp.dto.ModalResponse;
import com.ricardo.enrollmentwebapp.user.MyUserService;
import com.ricardo.enrollmentwebapp.dto.RegistrationRequest;
import com.ricardo.enrollmentwebapp.dto.ResetRequest;
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
        return "registration";
    }

    @PostMapping("/register")
    public ResponseEntity<ModalResponse> registerUser(@RequestBody RegistrationRequest request)
    {
        return myUserService.register(request);
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
    //@ResponseBody
    public ResponseEntity<ModalResponse> resetRequest(@RequestParam String username)
    {
        return myUserService.sendResetEmail(username);
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token, Model model)
    {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    // @ResponseBody // ResponseBody is not needed when returning a ResponseEntity, as ResponseEntity return a full Http response with a body
    public ResponseEntity<ModalResponse> resetPassword(@RequestBody ResetRequest request)
    {
        return myUserService.resetPassword(request.token(), request.password());
        // return ResponseEntity.ok().body(new ModalResponse("Password reset successful", "Password reset successful"));
    }
}
