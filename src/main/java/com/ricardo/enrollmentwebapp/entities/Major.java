package com.ricardo.enrollmentwebapp.entities;

import com.ricardo.enrollmentwebapp.entities.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_majors")
public class Major
{
    @Id
    @Column(length = 10)
    private String code;
    private String name;

    @ManyToMany(targetEntity = Course.class)
    private List<Course> courses; // one major has many courses

    @ManyToMany(targetEntity = Course.class)
    private List<Course> distributiveRequirementCourses;
}
