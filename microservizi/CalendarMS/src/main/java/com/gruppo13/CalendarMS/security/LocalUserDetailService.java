package com.gruppo13.CalendarMS.security;

import com.gruppo13.CalendarMS.dto.LocalUser;
import com.gruppo13.CalendarMS.exception.ResourceNotFoundException;
import com.gruppo13.CalendarMS.models.User;
import com.gruppo13.CalendarMS.service.UserService;
import com.gruppo13.CalendarMS.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("localUserDetailService")
public class LocalUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
        return createLocalUser(user);
    }

    @Transactional
    public LocalUser loadUserById(Long id) {
        User user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return createLocalUser(user);
    }


    private LocalUser createLocalUser(User user) {
        return new LocalUser(user.getEmail(), user.getPassword(), true, true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()), user);
    }
}