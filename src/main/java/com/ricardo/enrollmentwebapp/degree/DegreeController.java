package com.example.demo.degree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DegreeController
{
    @Autowired
    DegreeService service;

    @PostMapping("/saveDegree")
    public Degree saveDegree(@RequestBody Degree degree)
    {
        return service.saveCourse(degree);
    }

    @PostMapping("/saveDegrees")
    public List<Degree> saveDegrees(@RequestBody List<Degree> degrees)
    {
        return service.saveDegrees(degrees);
    }

    @GetMapping("/getDegrees")
    public List<Degree> getAllDegrees()
    {
        return service.getAllDegrees();
    }

    @GetMapping("/getDegree/{code}")
    public Degree getDegreeByCode(@PathVariable String code)
    {
        return service.getDegreeByCode(code);
    }

    @DeleteMapping("/deleteDegreeByCode/{code}")
    public String deleteDegreeByCode(@PathVariable String code)
    {
        return service.deleteDegreeByCode(code);
    }
}