package com.gruppo13.CoursesMS.service.serviceImpl;

import com.gruppo13.CoursesMS.model.User;
import com.gruppo13.CoursesMS.repository.UserRepository;
import com.gruppo13.CoursesMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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