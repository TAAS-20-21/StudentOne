package com.gruppo13.ChatMS.service.serviceImpl;


import com.gruppo13.ChatMS.model.Chat;
import com.gruppo13.ChatMS.model.User;
import com.gruppo13.ChatMS.repositories.ChatRepository;
import com.gruppo13.ChatMS.repositories.UserRepository;
import com.gruppo13.ChatMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}