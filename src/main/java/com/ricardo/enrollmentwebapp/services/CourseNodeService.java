package com.ricardo.enrollmentwebapp.services;

import com.ricardo.enrollmentwebapp.dto.CourseNodeDto;
import com.ricardo.enrollmentwebapp.entities.Course;
import com.ricardo.enrollmentwebapp.entities.CourseNode;
import com.ricardo.enrollmentwebapp.repositories.CourseNodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        Optional<CourseNode> courseNode = courseNodeRepository.findByMajorCodeAndCourseCode(majorCode, dto.getCourseCode());

        if (courseNode.isPresent())
        {
            return courseNode.get();
        }

        Course course = courseService.getCourseByCode(dto.getCourseCode()).orElseThrow();
        List<Course> hardReqs = courseService.getCoursesByCodes(dto.getHardReqsCodes());
        List<Course> softReqs = courseService.getCoursesByCodes(dto.getSoftReqsCodes());

        CourseNode newNode = new CourseNode(
                null,
                majorCode,
                course,
                hardReqs,
                softReqs,
                dto.getSpecialReqs(),
                dto.getIsDistCourse(),
                dto.getIndex()
        );

        return courseNodeRepository.save(newNode);
    }
}
