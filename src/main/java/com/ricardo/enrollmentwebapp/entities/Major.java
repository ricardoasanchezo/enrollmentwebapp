package com.ricardo.enrollmentwebapp.entities;
import com.ricardo.enrollmentwebapp.dto.MajorCodeName;
import com.ricardo.enrollmentwebapp.dto.MajorDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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

    public MajorDto toDto()
    {
        return new MajorDto(
                code,
                name,
                courseNodes.stream().map(CourseNode::toDto).collect(Collectors.toList()),
                distCredits,
                electCredits
        );
    }

    public MajorCodeName toMajorCodeName()
    {
        return new MajorCodeName(
                code,
                name
        );
    }
}