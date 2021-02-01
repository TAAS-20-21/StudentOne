package com.gruppo13.CalendarMS.repositories;

import com.gruppo13.CalendarMS.models.CustomEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<CustomEvent, Long> {

}
