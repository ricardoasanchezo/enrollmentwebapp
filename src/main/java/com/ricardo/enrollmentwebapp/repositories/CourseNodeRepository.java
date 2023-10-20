package com.ricardo.enrollmentwebapp.repositories;

import com.ricardo.enrollmentwebapp.entities.CourseNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CourseNodeRepository extends JpaRepository<CourseNode, Long>
{
    @Query("SELECT node FROM CourseNode node WHERE node.majorCode = ?1 AND node.course.code = ?2")
    Optional<CourseNode> findByMajorCodeAndCourseCode(String majorCode, String courseCode);
}
