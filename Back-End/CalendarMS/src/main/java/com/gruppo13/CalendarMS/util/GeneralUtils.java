package com.gruppo13.CalendarMS.util;


import com.gruppo13.CalendarMS.models.Role;
import com.gruppo13.CalendarMS.models.TypeRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class GeneralUtils {

    public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getTypeRole().toString()));
        }
        return authorities;
    }
}