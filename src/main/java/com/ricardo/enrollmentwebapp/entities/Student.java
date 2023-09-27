package com.ricardo.enrollmentwebapp.entities;

import com.ricardo.enrollmentwebapp.dto.StudentCourseDetailedDto;
import com.ricardo.enrollmentwebapp.entities.Major;
import com.ricardo.enrollmentwebapp.entities.Course;
import com.ricardo.enrollmentwebapp.services.CourseFilterService;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_students")
public class Student
{
    @Id
    @Column(length = 12)
    private String id;
    private String email;

    @ManyToOne(targetEntity = Major.class)
    private Major major;

    @ManyToMany(targetEntity = Course.class)
    private List<Course> approvedCourses;

//    public StudentCourseDetailedDto getDetailedStudentCourses(String id)
//    {
//        List<Course> approvedMajorCourses = new ArrayList<>();
//        List<Course> remainingMajorCourses = new ArrayList<>();
//
//        for (Course course : major.getCourses())
//        {
//            if (approvedCourses.contains(course))
//                approvedMajorCourses.add(course);
//            else
//                remainingMajorCourses.add(course);
//        }
//
//        // Filter appreciation courses
//        CourseFilterService courseFilterService = new CourseFilterService();
//
//        courseFilterService.filterCourses(approvedMajorCourses, remainingMajorCourses, CourseFilterService.APPRECIATION_CODES);
//        // Filter history courses
//        courseFilterService.filterCourses(approvedMajorCourses, remainingMajorCourses, CourseFilterService.HISTORY_CODES);
//        // Filter english courses
//        courseFilterService.filterEnglishCourses(approvedMajorCourses, remainingMajorCourses);
//
//        List<Course> approvedDistributiveCourses = new ArrayList<>();
//        List<Course> remainingDistributiveCourses = new ArrayList<>();
//
//        for (Course course : major.getDistributiveRequirementCourses())
//        {
//            boolean isApproved = approvedCourses.contains(course);
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
//                id,
//                major.getName(),
//                approvedMajorCourses,
//                remainingMajorCourses,
//                approvedDistributiveCourses,
//                remainingDistributiveCourses
//        );
//    }
}