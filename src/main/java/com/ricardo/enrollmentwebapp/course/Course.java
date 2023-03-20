package com.ricardo.enrollmentwebapp.course;

import com.ricardo.enrollmentwebapp.department.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_courses")
public class Course
{
    @Id
    @Column(length = 10)
    private String code;
    private String title;
    private int creditCount;
//    @ManyToMany
//    private Set<Course> prerequisites;
//    @ManyToMany
//    private Set<Course> corequisites;
    @ManyToOne
    private Department department; // Many courses have one and only one department
}