package com.ricardo.enrollmentwebapp.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String studentId;
    private String email;
    private String sequentialCode;

//    @ManyToMany
//    private Set<Course> approvedCourses;
}
