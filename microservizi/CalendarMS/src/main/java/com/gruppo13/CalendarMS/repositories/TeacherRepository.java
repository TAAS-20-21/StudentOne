package com.gruppo13.CalendarMS.repositories;

import com.gruppo13.CalendarMS.models.Teacher;
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
    List<Long> getCourseIdByStudent(Long studentId);
}
