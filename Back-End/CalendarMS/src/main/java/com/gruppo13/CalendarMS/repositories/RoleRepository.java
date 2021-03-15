package com.gruppo13.CalendarMS.repositories;

import com.gruppo13.CalendarMS.models.Role;
import com.gruppo13.CalendarMS.models.TypeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByTypeRole(TypeRole typeRole);
    Boolean existsByTypeRole(TypeRole typeRole);
}