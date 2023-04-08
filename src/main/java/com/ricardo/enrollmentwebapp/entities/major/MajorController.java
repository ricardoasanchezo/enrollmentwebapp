package com.ricardo.enrollmentwebapp.entities.major;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/degrees")
public class MajorController
{
    @Autowired
    MajorService majorService;

    @GetMapping("/getAll")
    public List<Major> getAllDegrees()
    {
        return majorService.getAllMajors();
    }

    @GetMapping("/get/{code}")
    public Major getDegreeByCode(@PathVariable String code) throws Exception
    {
        return majorService.getMajorByCode(code);
    }
}