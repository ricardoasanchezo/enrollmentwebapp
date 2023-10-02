package com.ricardo.enrollmentwebapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_course_nodes")
public class CourseNode
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String code;

    @ManyToOne(targetEntity = Course.class)
    private Course course;

    @ManyToMany(targetEntity = Course.class)
    private List<Course> hardReqs;

    @ManyToMany(targetEntity = Course.class)
    private List<Course> softReqs;

    private String specialReqs;

    private boolean isDistCourse;

    private int nodeIndex;
}
