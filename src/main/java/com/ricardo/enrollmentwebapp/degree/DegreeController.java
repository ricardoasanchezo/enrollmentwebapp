package com.ricardo.enrollmentwebapp.degree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/degrees")
public class DegreeController
{
    @Autowired
    DegreeService service;

    @PostMapping("/save")
    public Degree saveDegree(@RequestBody Degree degree)
    {
        return service.saveCourse(degree);
    }

    @PostMapping("/saveAll")
    public List<Degree> saveDegrees(@RequestBody List<Degree> degrees)
    {
        return service.saveDegrees(degrees);
    }

    @GetMapping("/getAll")
    public List<Degree> getAllDegrees()
    {
        return service.getAllDegrees();
    }

    @GetMapping("/get/{code}")
    public Degree getDegreeByCode(@PathVariable String code)
    {
        return service.getDegreeByCode(code);
    }

    @DeleteMapping("/delete/{code}")
    public String deleteDegreeByCode(@PathVariable String code)
    {
        return service.deleteDegreeByCode(code);
    }
}