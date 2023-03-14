package com.example.demo.course;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_course")
public class Course
{
    @Id
    @Column(length = 12)
    private String code;
    @Column(length = 6)
    private String subject;
    @Column(length = 6)
    private String number;
    private String title;
    private int creditCount;
    private int deptNumber;
}
