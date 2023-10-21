package com.ricardo.enrollmentwebapp.entities;
import com.ricardo.enrollmentwebapp.dto.MajorCodeName;
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
    @Column(length = 20)
    private String code; // Major code - year. Ex: 120-2019

    private String name;

    @ManyToMany(targetEntity = CourseNode.class)
    private List<CourseNode> courseNodes;

    private Integer distCredits;

    private Integer electCredits;

    public MajorCodeName toMajorDto()
    {
        return new MajorCodeName(
                code,
                name
        );
    }
}