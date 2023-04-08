package com.ricardo.enrollmentwebapp.entities.major;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MajorService
{
    private final MajorRepository majorRepository;

    public List<Major> getAllMajors()
    {
        return majorRepository.findAll();
    }

    public Major getMajorByCode(String code) throws Exception
    {
        return majorRepository.findById(code).orElseThrow(() -> new Exception("Major not found: " + code));
    }
}
