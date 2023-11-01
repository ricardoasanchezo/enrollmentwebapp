package com.ricardo.enrollmentwebapp.controllers;

import com.ricardo.enrollmentwebapp.dto.MajorCodeName;
import com.ricardo.enrollmentwebapp.dto.MajorCreationRequest;
import com.ricardo.enrollmentwebapp.dto.MajorDto;
import com.ricardo.enrollmentwebapp.services.MajorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/major")
@AllArgsConstructor
public class MajorController
{
    private final MajorService majorService;

//    @GetMapping("/{code}")
//    public Major getMajor(@PathVariable String code)
//    {
//        return majorService.findById(code);
//    }

    @GetMapping("/{code}")
    public MajorDto getMajor(@PathVariable String code)
    {
        return majorService.findById(code).toDto();
    }

    @GetMapping("/getAllCodeNames")
    public List<MajorCodeName> getMajorCodeNames()
    {
        return majorService.getAllMajorCodeNames();
    }

    @PostMapping("/add")
    public void addMajor(@RequestBody MajorCreationRequest request)
    {
        majorService.addMajor(request);
    }
}
