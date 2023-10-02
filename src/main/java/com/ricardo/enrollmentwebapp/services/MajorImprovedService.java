package com.ricardo.enrollmentwebapp.services;

import com.ricardo.enrollmentwebapp.dto.MajorCodeName;
import com.ricardo.enrollmentwebapp.dto.MajorCreationRequest;
import com.ricardo.enrollmentwebapp.dto.MajorUpdateRequest;
import com.ricardo.enrollmentwebapp.entities.CourseNode;
import com.ricardo.enrollmentwebapp.entities.MajorImproved;
import com.ricardo.enrollmentwebapp.repositories.MajorImprovedRepository;
import com.ricardo.enrollmentwebapp.utils.InputValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MajorImprovedService
{
    private final MajorImprovedRepository majorImprovedRepository;
    private final CourseNodeService courseNodeService;

    public MajorImproved findById(String code)
    {
        return majorImprovedRepository.findById(code).orElseThrow();
    }

    public List<MajorCodeName> getAllMajorCodeNames()
    {
        return majorImprovedRepository.findAll().stream().map(MajorImproved::toMajorDto).toList();
    }

    public void addMajor(MajorCreationRequest request)
    {
        if (!InputValidator.isValidMajorCreationRequest(request))
            throw new RuntimeException("Creation request is not valid");

        String code = request.getCode() + "-" + request.getYear();

        if (majorImprovedRepository.findById(code).isPresent())
            throw new RuntimeException("Major with this code already exists");

        MajorImproved major = new MajorImproved(
                code,
                request.getName(),
                new ArrayList<>(),
                request.getDistCredits(),
                request.getElectCredits()
        );

        majorImprovedRepository.save(major);
    }

//    public void addCourseToMajor(String code, CourseNodeShell shell)
//    {
//        CourseNode courseNode = courseNodeService.getCourseNodeFromShell(shell);
//
//        var major = majorImprovedRepository.findById(code).orElseThrow();
//
//        if (major.getAllCourseCodes().contains(shell.courseCode()))
//            throw new RuntimeException("Major already contains course");
//
////        boolean majorContainsRequiredCourses =
////                major.getAllCourseCodes().containsAll(shell.hardReqsCodes()) &&
////                major.getAllCourseCodes().containsAll(shell.softReqsCodes());
////
////        if (!majorContainsRequiredCourses)
////            throw new RuntimeException("Major does not contain the courses required by the new course");
//
//        courseNodeService.save(courseNode);
//
//        major.getCoursesNodes().add(courseNode);
//
//        majorImprovedRepository.save(major);
//    }

    public void updateMajor(MajorUpdateRequest request)
    {
        Optional<MajorImproved> majorOptional = majorImprovedRepository.findById(request.code());

        if (majorOptional.isEmpty())
            throw new RuntimeException("Major with code: " + request.code() + " does not exist");

        List<CourseNode> requestNodes = request.courseNodeShellList()
                .stream().map(shell -> courseNodeService.getCourseNodeFromShell(request.code(), shell)).toList();

        MajorImproved updatedMajor = new MajorImproved(
                request.code(),
                request.name(),
                requestNodes,
                request.distCredits(),
                request.electCredits()
        );

        majorImprovedRepository.deleteById(request.code());
        majorImprovedRepository.save(updatedMajor);
    }
}
