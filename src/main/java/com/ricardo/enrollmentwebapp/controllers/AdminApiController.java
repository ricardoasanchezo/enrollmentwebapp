package com.ricardo.enrollmentwebapp.controllers;

import com.ricardo.enrollmentwebapp.dto.MajorUpdateRequest;
import com.ricardo.enrollmentwebapp.services.MajorImprovedService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class AdminApiController
{
    private final MajorImprovedService majorImprovedService;


    @PutMapping("/major/{code}/update")
    @PreAuthorize("hasAuthority('admin:update')")
    public void updateMajor(@RequestBody MajorUpdateRequest majorUpdateRequest)
    {
        majorImprovedService.updateMajor(majorUpdateRequest);
    }
}