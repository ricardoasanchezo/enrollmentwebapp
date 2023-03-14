package com.example.demo.degree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DegreeService
{
    @Autowired
    DegreeRepository repository;

    public Degree saveCourse(Degree degree)
    {
        return repository.save(degree);
    }

    public List<Degree> saveDegrees(List<Degree> degrees)
    {
        return repository.saveAll(degrees);
    }

    public List<Degree> getAllDegrees()
    {
        return repository.findAll();
    }

    public Degree getDegreeByCode(String code)
    {
        return repository.findById(code).orElse(null);
    }

    public String deleteDegreeByCode(String code)
    {
        repository.deleteById(code);
        return "Degree with code: " + code + " deleted!";
    }
}
