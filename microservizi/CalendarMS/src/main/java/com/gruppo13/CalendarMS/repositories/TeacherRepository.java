package com.gruppo13.CalendarMS.repositories;

import com.gruppo13.CalendarMS.models.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;

@Repository
public interface TeacherRepository extends JpaRepository<Docente, Long> {

    Docente findByEmail(String email);

    boolean existsByEmail(String email);
}
