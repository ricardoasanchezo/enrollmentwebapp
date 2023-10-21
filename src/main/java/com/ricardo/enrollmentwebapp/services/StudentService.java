package com.ricardo.enrollmentwebapp.services;


import com.ricardo.enrollmentwebapp.entities.Course;
import com.ricardo.enrollmentwebapp.entities.Major;
import com.ricardo.enrollmentwebapp.entities.Student;
import com.ricardo.enrollmentwebapp.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService
{
    private final StudentRepository studentRepository;
    private final CourseFilterService courseFilterService;

    public Optional<Student> findStudentById(String id) // throws Exception
    {
        return studentRepository.findById(id);
    }

//    public List<Course> getApprovedCoursesInMajor(String id) throws Exception
//    {
//        // TODO: configure get courses from student so searching by student ID in database is case insensitive
//
//        if (findStudentById(id).isEmpty())
//            throw new Exception("Student was not found");
//
//        Student student = findStudentById(id).get();
//        Major major = student.getMajor();
//
//        List<Course> approvedCourses = student.getApprovedCourses();
//        List<Course> majorCourses = major.getCourses();
//        List<Course> approvedCoursesInMajor = new ArrayList<>();
//
//        for (Course course: approvedCourses)
//        {
//            if (majorCourses.contains(course))
//                approvedCoursesInMajor.add(course);
//        }
//
//        return approvedCoursesInMajor;
//    }

//    public StudentCourseDetailedDto getDetailedStudentCourses(String id) throws Exception
//    {
//        if (findStudentById(id).isEmpty())
//            throw new Exception("Student was not found");
//
//        Student student = findStudentById(id).get();
//        Major major = student.getMajor();
//
//        List<Course> approvedCourses = new ArrayList<>();
//        List<Course> remainingCourses = new ArrayList<>();
//
//        for (Course course: major.getCourses())
//        {
//            boolean isApproved = student.getApprovedCourses().contains(course);
//            if (isApproved)
//                approvedCourses.add(course);
//            else
//                remainingCourses.add(course);
//        }
//
//        // Filter appreciation courses
//        courseFilterService.filterCourses(approvedCourses, remainingCourses, CourseFilterService.APPRECIATION_CODES);
//        // Filter history courses
//        courseFilterService.filterCourses(approvedCourses, remainingCourses, CourseFilterService.HISTORY_CODES);
//        // Filter english courses
//        courseFilterService.filterEnglishCourses(approvedCourses, remainingCourses);
//
//        List<Course> approvedDistributiveCourses = new ArrayList<>();
//        List<Course> remainingDistributiveCourses = new ArrayList<>();
//
//        for (Course course: major.getDistributiveRequirementCourses())
//        {
//            boolean isApproved = student.getApprovedCourses().contains(course);
//            if (isApproved)
//                approvedDistributiveCourses.add(course);
//            else
//                remainingDistributiveCourses.add(course);
//        }
//
//        // Filter distributive courses
//        courseFilterService.filterDistributiveCourses(major, approvedDistributiveCourses, remainingDistributiveCourses);
//
//        return new StudentCourseDetailedDto(
//                student.getId(),
//                major.getName(),
//                approvedCourses,
//                remainingCourses,
//                approvedDistributiveCourses,
//                remainingDistributiveCourses
//        );
//    }


}
