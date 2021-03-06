package com.gruppo13.AuthenticationMS.repository;
import com.gruppo13.AuthenticationMS.model.Role;
import com.gruppo13.AuthenticationMS.model.TypeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByTypeRole(TypeRole typeRole);
    Boolean existsByTypeRole(TypeRole typeRole);
}