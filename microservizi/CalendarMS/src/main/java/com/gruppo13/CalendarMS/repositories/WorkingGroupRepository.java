package com.gruppo13.CalendarMS.repositories;

import com.gruppo13.CalendarMS.models.WorkingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingGroupRepository extends JpaRepository<WorkingGroup, Long> {

    WorkingGroup findByName(String name);

    boolean existsByName(String name);

    @Query(value = "SELECT wgm.working_group_id FROM working_group_members wgm WHERE wgm.student_id = ?1", nativeQuery = true)
    List<Long> getGroupIdByStudent(Long studentId);
}
