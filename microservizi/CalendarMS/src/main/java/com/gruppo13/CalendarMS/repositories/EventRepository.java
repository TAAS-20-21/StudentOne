package com.gruppo13.CalendarMS.repositories;

import com.gruppo13.CalendarMS.models.CustomEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<CustomEvent, Long> {

    boolean existsById(Long id);

    boolean existsByGoogleId(String googleId);

    List<CustomEvent> findByCourseId(Long courseId);

    List<CustomEvent> findByWorkingGroupId(Long wkId);

    CustomEvent findByAngularId(Long aId);

    void deleteByGoogleId(String gId);

    @Query(value = "SELECT MAX(e.angular_id) FROM event e", nativeQuery = true)
    Long maxAngularId();

}
