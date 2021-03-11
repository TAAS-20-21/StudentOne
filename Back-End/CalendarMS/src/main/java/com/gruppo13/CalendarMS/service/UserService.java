package com.gruppo13.CalendarMS.service;

import com.gruppo13.CalendarMS.models.User;


import java.util.Optional;


public interface UserService {


    User findUserByEmail(String email);

    Optional<User> findUserById(Long id);

}