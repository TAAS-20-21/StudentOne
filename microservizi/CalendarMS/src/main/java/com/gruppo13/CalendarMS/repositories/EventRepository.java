package com.gruppo13.CalendarMS.repositories;

import com.gruppo13.CalendarMS.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    boolean existsById(Long id);
}
