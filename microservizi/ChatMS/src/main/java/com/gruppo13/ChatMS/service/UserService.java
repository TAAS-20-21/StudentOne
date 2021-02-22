package com.gruppo13.ChatMS.service;


import com.gruppo13.ChatMS.model.Chat;
import com.gruppo13.ChatMS.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface UserService {

    List<User> getAllUsers();

    User findUserByEmail(String email);

    Optional<User> findUserById(Long id);

}