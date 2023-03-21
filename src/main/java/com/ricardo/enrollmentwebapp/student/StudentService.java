package com.ricardo.enrollmentwebapp.student;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService
{
    @Autowired
    StudentRepository studentRepository;

    public Optional<Student> findStudentById(String studentId)
    {
        return studentRepository.findByStudentId(studentId);
    }
}
