package com.gruppo13.CalendarMS.repositories;

import com.gruppo13.CalendarMS.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(value = "SELECT cl.course_id FROM course_like cl WHERE cl.student_id = ?1", nativeQuery = true)
    List<Long> getCourseIdByStudent(Long studentId);

    @Query(value = "INSERT INTO course_like(student_id, course_id) VALUES (?2, ?1) RETURNING *", nativeQuery = true)
    Object addLikedCourse(Long courseId, Long teacherId);

    @Query(value = "DELETE FROM course_like WHERE course_id = ?1 AND student_id = ?2 RETURNING *", nativeQuery = true)
    Object deleteLikedCourse(Long courseId, Long teacherId);

}
