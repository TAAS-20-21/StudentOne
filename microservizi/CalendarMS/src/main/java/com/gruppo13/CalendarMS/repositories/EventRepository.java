package com.gruppo13.CalendarMS.repositories;

import com.gruppo13.CalendarMS.models.CustomEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<CustomEvent, Long> {

    boolean existsById(Long id);

    List<CustomEvent> findByCourseId(Long courseId);

    List<CustomEvent> findByWorkingGroupId(Long wkId);

}
