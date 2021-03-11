package com.gruppo13.CoursesMS.service;

import com.gruppo13.CoursesMS.model.User;

import java.util.Optional;


public interface UserService {


    User findUserByEmail(String email);

    Optional<User> findUserById(Long id);

}