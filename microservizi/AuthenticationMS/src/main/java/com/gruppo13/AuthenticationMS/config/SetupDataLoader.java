package com.gruppo13.AuthenticationMS.config;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.gruppo13.AuthenticationMS.dto.SocialProvider;
import com.gruppo13.AuthenticationMS.model.Role;
import com.gruppo13.AuthenticationMS.model.TypeRole;
import com.gruppo13.AuthenticationMS.model.User;
import com.gruppo13.AuthenticationMS.repository.RoleRepository;
import com.gruppo13.AuthenticationMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.annotation.Order;


@Configuration
public class SetupDataLoader{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @Order(0)
    public void buildRoleName() {
        if(!roleRepository.existsByTypeRole(TypeRole.ROLE_USER)) {
            Role roleUser = new Role(TypeRole.ROLE_USER);
            roleRepository.save(roleUser);
        }

        if(!roleRepository.existsByTypeRole(TypeRole.ROLE_ADMIN)) {
            Role roleAdmin = new Role(TypeRole.ROLE_ADMIN);
            roleRepository.save(roleAdmin);
        }

        if(!roleRepository.existsByTypeRole(TypeRole.ROLE_COORDINATOR)) {
            Role roleHR = new Role(TypeRole.ROLE_COORDINATOR);
            roleRepository.save(roleHR);
        }
    }
    @Bean
    @Order(1)
    public void creaUserAdmin() {
        User user = null;
        if (userRepository.count() == 0){

            user = new User();
            user.setEmail("studentone@admin.it");
            user.setPassword(passwordEncoder.encode("studentone"));
            Set<Role> roles = new HashSet<Role>();
            roles.add(roleRepository.findByTypeRole(TypeRole.ROLE_ADMIN).orElse(null));
            user.setRoles(roles);
            userRepository.save(user);

        }
    }
}