package com.gruppo13.CalendarMS.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * The persistent class for the role database table.
 *
 */
public class Role implements Serializable {
    private static final long serialVersionUID = 8837453630224340272L;

    private Long id;

    private TypeRole typeRole;

    private Set<User> users;

    public Role(TypeRole typeRole) {
        this.typeRole = typeRole;
    }

    public Role() {

    }

    public TypeRole getTypeRole() {
        return typeRole;
    }

    public void setTypeRole(TypeRole typeRole) {
        this.typeRole = typeRole;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}