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

    public CourseNode getCourseNodeFromShell(String majorCode, CourseNodeDto shell)
    {
        Optional<CourseNode> courseNode = courseNodeRepository.findByMajorCodeAndCourseCode(majorCode, shell.courseCode());

        if (courseNode.isPresent())
        {
            return courseNode.get();
        }
        else
        {
            Course course = courseService.getCourseByCode(shell.courseCode());
            List<Course> hardReqs = courseService.getCoursesByCodes(shell.hardReqsCodes());
            List<Course> softReqs = courseService.getCoursesByCodes(shell.softReqsCodes());

            CourseNode newNode = new CourseNode(
                    null,
                    majorCode,
                    course,
                    hardReqs,
                    softReqs,
                    shell.specialReqs(),
                    shell.isDistCourse(),
                    shell.index()
            );

            courseNodeRepository.save(newNode);

            return newNode;
        }
    }

//    public void save(CourseNode courseNode)
//    {
//        courseNodeRepository.save(courseNode);
//    }
}
