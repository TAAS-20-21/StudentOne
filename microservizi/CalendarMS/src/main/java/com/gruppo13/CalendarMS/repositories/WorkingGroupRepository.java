package com.gruppo13.CalendarMS.repositories;

import com.gruppo13.CalendarMS.models.Course;
import com.gruppo13.CalendarMS.models.WorkingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingGroupRepository extends JpaRepository<WorkingGroup, Long> {

    WorkingGroup findByName(String name);

    boolean existsByName(String name);
}
