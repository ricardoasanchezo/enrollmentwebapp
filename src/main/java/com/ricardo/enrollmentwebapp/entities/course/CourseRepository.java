package com.ricardo.enrollmentwebapp.entities.course;

import com.ricardo.enrollmentwebapp.entities.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String>
{
}
