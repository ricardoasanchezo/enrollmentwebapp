package com.ricardo.enrollmentwebapp.entities.department;

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
@Table(name = "tbl_departments")
public class Department
{
    @Id
    @Column(length = 6)
    private String code;
    private int number;
    private String name;
}
