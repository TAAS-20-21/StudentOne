package com.gruppo13.CoursesMS.repository;

import com.gruppo13.CoursesMS.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher findByEmail(String email);

    boolean existsByEmail(String email);

    //FAI QUERY PER OTTENERE I CORSI ASSEGNATI
    @Query(value = "SELECT ac.course_id FROM assigned_courses ac WHERE ac.teacher_id = ?1", nativeQuery = true)
    List<Long> getCourseIdByTeacher(Long teacherId);

    @Query(value = "INSERT INTO assigned_courses(teacher_id, course_id) VALUES (?2, ?1) RETURNING *", nativeQuery = true)
    Object addAssignedCourse(Long courseId, Long teacherId);

    @Query(value = "DELETE FROM assigned_courses WHERE course_id = ?1 AND teacher_id = ?2 RETURNING *", nativeQuery = true)
    Object deleteAssignedCourse(Long courseId, Long teacherId);

}
