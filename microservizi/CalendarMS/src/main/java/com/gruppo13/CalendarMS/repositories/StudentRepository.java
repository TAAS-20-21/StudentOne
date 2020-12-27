package com.gruppo13.CalendarMS.repositories;

import com.gruppo13.CalendarMS.models.Studente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Studente, Long> {

    Studente findByEmail(String email);

    boolean existsByEmail(String email);
}
