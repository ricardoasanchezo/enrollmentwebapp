package com.ricardo.enrollmentwebapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_available_courses")
public class AvailableCourse
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String term;

    @ManyToOne(targetEntity = Course.class)
    private Course course;

    private String section;

    private String modality;

    private String building;

    private String classroom;

    private String days;

    private Integer beginTime;

    private Integer endTime;
}
