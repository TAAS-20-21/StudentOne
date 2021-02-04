package com.gruppo13.CalendarMS.services;

import com.google.api.client.auth.oauth2.Credential;
import com.gruppo13.CalendarMS.models.User;


import java.util.Map;
import java.util.Optional;


public interface UserService {


    User findUserByEmail(String email);

    Optional<User> findUserById(Long id);

}