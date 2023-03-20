package com.ricardo.enrollmentwebapp.course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String>
{
}
