package com.ricardo.enrollmentwebapp.services;

import com.ricardo.enrollmentwebapp.dto.CourseNodeDto;
import com.ricardo.enrollmentwebapp.entities.Course;
import com.ricardo.enrollmentwebapp.entities.CourseNode;
import com.ricardo.enrollmentwebapp.repositories.CourseNodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseNodeService
{
    private final CourseNodeRepository courseNodeRepository;
    private final CourseService courseService;


    public CourseNode getCourseNodeFromDto(String majorCode, CourseNodeDto dto)
    {
        Optional<CourseNode> courseNodeOptional = courseNodeRepository.findByMajorCodeAndCourseCode(majorCode, dto.getCode());

        if (courseNodeOptional.isPresent())
            return updateCourseNode(courseNodeOptional.get(), dto);
        else
            return createNewCourseNode(majorCode, dto);
    }

    private CourseNode updateCourseNode(CourseNode node, CourseNodeDto dto)
    {
        node.setPassingGrade(dto.getPassingGrade());
        node.setHardReqs(getCourseListFromCodes(dto.getHardReqs()));
        node.setSoftReqs(getCourseListFromCodes(dto.getSoftReqs()));
        node.setSpecialReqs(dto.getSpecialReqs());
        node.setIsDistCourse(dto.getIsDistCourse());
        node.setNodeIndex(dto.getNodeIndex());

        return courseNodeRepository.save(node);
    }

    private CourseNode createNewCourseNode(String majorCode, CourseNodeDto dto)
    {
        Course course = courseService.getCourseByCode(dto.getCode()).orElseThrow();
        List<Course> hardReqs = getCourseListFromCodes(dto.getHardReqs());
        List<Course> softReqs = getCourseListFromCodes(dto.getSoftReqs());

        CourseNode newNode = new CourseNode(
                null,
                majorCode,
                course,
                dto.getPassingGrade(),
                hardReqs,
                softReqs,
                dto.getSpecialReqs(),
                dto.getIsDistCourse(),
                dto.getNodeIndex()
        );

        return courseNodeRepository.save(newNode);
    }

    private List<Course> getCourseListFromCodes(List<String> codes)
    {
        List<Course> courses = new ArrayList<>();

        for (String code: codes)
            courses.add(courseService.getCourseByCode(code).orElseThrow());

        return courses;
    }
}
