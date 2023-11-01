package com.ricardo.enrollmentwebapp.services;

import com.ricardo.enrollmentwebapp.dto.MajorCodeName;
import com.ricardo.enrollmentwebapp.dto.MajorCreationRequest;
import com.ricardo.enrollmentwebapp.dto.MajorDto;
import com.ricardo.enrollmentwebapp.entities.CourseNode;
import com.ricardo.enrollmentwebapp.entities.Major;
import com.ricardo.enrollmentwebapp.repositories.MajorRepository;
import com.ricardo.enrollmentwebapp.utils.InputValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MajorService
{
    private final MajorRepository majorRepository;
    private final CourseNodeService courseNodeService;

    public Major findById(String code)
    {
        return majorRepository.findById(code).orElseThrow();
    }

    public List<MajorCodeName> getAllMajorCodeNames()
    {
        return majorRepository.findAll().stream().map(Major::toMajorCodeName).toList();
    }

    public void addMajor(MajorCreationRequest request)
    {
        if (!InputValidator.isValidMajorCreationRequest(request))
            throw new RuntimeException("Creation request is not valid");

        String code = request.getCode() + "-" + request.getYear();

        if (majorRepository.findById(code).isPresent())
            throw new RuntimeException("Major with this code already exists");

        Major major = new Major(
                code,
                request.getName(),
                new ArrayList<>(),
                request.getDistCredits(),
                request.getElectCredits()
        );

        majorRepository.save(major);
    }

    public void updateMajor(MajorDto majorDto)
    {
        Major major = majorRepository.findById(majorDto.getCode()).orElseThrow();

        major.setName(majorDto.getName());
        major.setDistCredits(majorDto.getDistCredits());
        major.setElectCredits(majorDto.getElectCredits());

        List<CourseNode> newNodes = majorDto.getCourseNodes().stream()
                .map(dto -> courseNodeService.getCourseNodeFromDto(majorDto.getCode(), dto))
                .collect(Collectors.toList());

        major.setCourseNodes(newNodes);

        majorRepository.save(major);
    }
}
