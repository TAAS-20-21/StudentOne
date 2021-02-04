package com.gruppo13.CalendarMS.services.serviceImpl;

import com.google.api.client.auth.oauth2.Credential;

import com.gruppo13.CalendarMS.models.User;
import com.gruppo13.CalendarMS.repositories.UserRepository;
import com.gruppo13.CalendarMS.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }


}