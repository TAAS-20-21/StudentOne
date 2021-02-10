package com.gruppo13.CalendarMS.repositories;

import com.gruppo13.CalendarMS.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher findByEmail(String email);

    boolean existsByEmail(String email);

    //FAI QUERY PER OTTENERE I CORSI ASSEGNATI
}
