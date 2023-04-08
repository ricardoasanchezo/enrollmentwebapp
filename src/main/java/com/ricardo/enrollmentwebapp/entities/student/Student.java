package com.ricardo.enrollmentwebapp.entities.student;

import com.ricardo.enrollmentwebapp.entities.major.Major;
import com.ricardo.enrollmentwebapp.entities.course.Course;
import jakarta.persistence.*;
import lombok.*;

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
}
