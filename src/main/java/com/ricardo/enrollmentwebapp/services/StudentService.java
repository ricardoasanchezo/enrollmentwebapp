package com.ricardo.enrollmentwebapp.services;

import com.ricardo.enrollmentwebapp.dto.StudentCourseDetailedDTO;
import com.ricardo.enrollmentwebapp.entities.Course;
import com.ricardo.enrollmentwebapp.entities.Major;
import com.ricardo.enrollmentwebapp.entities.Student;
import com.ricardo.enrollmentwebapp.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService
{
    private final StudentRepository studentRepository;

    public Student findStudentById(String id) // throws Exception
    {
        return studentRepository.findById(id).orElse(null);
        // return studentRepository.findById(id).orElseThrow();
    }

    public List<Course> getApprovedCourses(String id)
    {
        return findStudentById(id).getApprovedCourses();
    }

    public List<Course> getApprovedCoursesInMajor(String id)
    {
        // TODO: configure get courses from student so searching by student ID in database is case insensitive

        Student student = findStudentById(id);
        Major major = student.getMajor();

        List<Course> approvedCourses = student.getApprovedCourses();
        List<Course> majorCourses = major.getCourses();
        List<Course> approvedCoursesInMajor = new ArrayList<>();

        for (Course course: approvedCourses)
        {
            if (majorCourses.contains(course))
                approvedCoursesInMajor.add(course);
        }

        return approvedCoursesInMajor;
    }

    public List<Course> getRemainingCoursesInMajor(String id)
    {
        Student student = findStudentById(id);
        Major major = student.getMajor();

        List<Course> approvedCourses = getApprovedCoursesInMajor(id);
        List<Course> remainingCourses = new ArrayList<>();

        for (Course majorCourse: major.getCourses())
        {
            boolean isApproved = approvedCourses.contains(majorCourse);
            if (!isApproved)
                remainingCourses.add(majorCourse);
        }

        return remainingCourses;
    }

    public List<Course> getRemainingDistributiveRequirements(String id)
    {
        Student student = findStudentById(id);
        Major major = student.getMajor();

        List<Course> approvedCourses = student.getApprovedCourses();
        List<Course> remainingDistributiveCourses = new ArrayList<>();

        for (Course course: major.getDistributiveRequirementCourses())
        {
            boolean isApproved = approvedCourses.contains(course);
            if (!isApproved)
                remainingDistributiveCourses.add(course);
        }

        return remainingDistributiveCourses;
    }

    public StudentCourseDetailedDTO getDetailedStudentCourses(String id)
    {
        Student student = findStudentById(id);
        Major major = student.getMajor();

        List<Course> approvedCourses = new ArrayList<>();
        List<Course> remainingCourses = new ArrayList<>();

        for (Course course: major.getCourses())
        {
            boolean isApproved = student.getApprovedCourses().contains(course);
            if (isApproved)
                approvedCourses.add(course);
            else
                remainingCourses.add(course);
        }

        List<Course> approvedDistributiveCourses = new ArrayList<>();
        List<Course> remainingDistributiveCourses = new ArrayList<>();

        for (Course course: major.getDistributiveRequirementCourses())
        {
            boolean isApproved = student.getApprovedCourses().contains(course);
            if (isApproved)
                approvedDistributiveCourses.add(course);
            else
                remainingDistributiveCourses.add(course);
        }

        return new StudentCourseDetailedDTO(
                student.getId(),
                major.getName(),
                approvedCourses,
                remainingCourses,
                approvedDistributiveCourses,
                remainingDistributiveCourses
        );
    }
}
