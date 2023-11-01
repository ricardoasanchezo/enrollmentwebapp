package com.ricardo.enrollmentwebapp.entities;

import com.ricardo.enrollmentwebapp.dto.CourseNodeDto;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String majorCode;

    @ManyToOne(targetEntity = Course.class)
    private Course course;

    private Character passingGrade;

    @ManyToMany(targetEntity = Course.class)
    private List<Course> hardReqs;

    @ManyToMany(targetEntity = Course.class)
    private List<Course> softReqs;

    private String specialReqs;

    private Boolean isDistCourse;

    private Integer nodeIndex;

    public CourseNodeDto toDto()
    {
        return new CourseNodeDto(
                course.getCode(),
                passingGrade,
                hardReqs.stream().map(Course::getCode).toList(),
                softReqs.stream().map(Course::getCode).toList(),
                specialReqs,
                isDistCourse,
                nodeIndex
        );
    }
}
