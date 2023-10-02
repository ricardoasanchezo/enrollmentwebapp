package com.ricardo.enrollmentwebapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
