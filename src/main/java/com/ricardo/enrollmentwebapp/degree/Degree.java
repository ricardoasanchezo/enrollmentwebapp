package com.ricardo.enrollmentwebapp.degree;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_degrees")
public class Degree
{
    @Id
    @Column(length = 10)
    private String code;
    private String name;
}
