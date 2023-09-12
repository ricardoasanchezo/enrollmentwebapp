package com.ricardo.enrollmentwebapp.repositories;

import com.ricardo.enrollmentwebapp.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface CourseRepository extends JpaRepository<Course, String>
{
}
