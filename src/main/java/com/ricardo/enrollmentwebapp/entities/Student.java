package com.ricardo.enrollmentwebapp.entities;

import com.ricardo.enrollmentwebapp.entities.Major;
import com.ricardo.enrollmentwebapp.entities.Course;
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
