package com.gruppo13.CoursesMS.repository;

import com.gruppo13.CoursesMS.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findByName(String name);

    boolean existsByName(String name);
}