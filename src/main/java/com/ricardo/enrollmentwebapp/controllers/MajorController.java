package com.ricardo.enrollmentwebapp.controllers;

import com.ricardo.enrollmentwebapp.dto.MajorCodeName;
import com.ricardo.enrollmentwebapp.dto.MajorCreationRequest;
import com.ricardo.enrollmentwebapp.entities.MajorImproved;
import com.ricardo.enrollmentwebapp.services.MajorImprovedService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/major")
@AllArgsConstructor
public class MajorController
{
    private final MajorImprovedService majorImprovedService;

    @GetMapping("/{code}")
    public MajorImproved getMajor(@PathVariable String code)
    {
        return majorImprovedService.findById(code);
    }

    @GetMapping("/getAllCodeNames")
    public List<MajorCodeName> getMajorCodeNames()
    {
        return majorImprovedService.getAllMajorCodeNames();
    }

    @PostMapping("/add")
    public void addMajor(@RequestBody MajorCreationRequest request)
    {
        majorImprovedService.addMajor(request);
    }

//    @PostMapping("{code}/addCourse")
//    public void addCourse(@PathVariable String code, @RequestBody CourseNodeShell courseNode)
//    {
//        majorImprovedService.addCourseToMajor(code, courseNode);
//    }
}
