package com.gruppo13.CoursesMS.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Map;


/**
 *
 * @author Chinna
 *
 */
public class LocalUser extends User {

    /**
     *
     */
    private static final long serialVersionUID = -2845160792248762779L;
    private Map<String, Object> attributes;
    private com.gruppo13.CoursesMS.model.User user;


    public LocalUser(final String userID, String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
                     final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, final com.gruppo13.CoursesMS.model.User user) {
        super(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getName() {
        return this.user.getName() + this.user.getSurname();
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public Map<String, Object> getClaims() {
        return this.attributes;
    }

    public com.gruppo13.CoursesMS.model.User getUser() {
        return user;
    }

}