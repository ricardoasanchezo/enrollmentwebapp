package com.ricardo.enrollmentwebapp.entities.student;

import com.ricardo.enrollmentwebapp.entities.course.Course;
import com.ricardo.enrollmentwebapp.entities.major.Major;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService
{
    private final StudentRepository studentRepository;
    // private final MajorService majorService;

    public Student findStudentById(String id) throws Exception
    {
        // return studentRepository.findById(id).orElseThrow(() -> new Exception("Student not found: " + id));
        return studentRepository.findById(id).orElseThrow();
    }

    public List<Course> getApprovedCourses(String id) throws Exception
    {
        return findStudentById(id).getApprovedCourses();
    }

    public List<Course> getApprovedCoursesInMajor(String studentId) throws Exception
    {
        // TODO: configure get courses from student so searching by student ID in database is case insensitive

        Student student = findStudentById(studentId);
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

//    public List<Course> getStudentNotApprovedCourses(String id) throws Exception
//    {
//        Student student = findStudentById(id);
//        Major major = student.getMajor();
//
//        for (:)
//        {
//
//        }
//    }
}
