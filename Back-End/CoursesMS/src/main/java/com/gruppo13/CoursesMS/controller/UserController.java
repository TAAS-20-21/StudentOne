package com.gruppo13.CoursesMS.controller;

import com.gruppo13.CoursesMS.model.User;
import com.gruppo13.CoursesMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @PostMapping(value = "/user/isProfessor")
    public ResponseEntity<Boolean> getIsProfessor(@RequestBody User user) {
        Optional<User> _user = userRepo.findById(user.getId());
        User newUser;
        if(_user != null){
            newUser = _user.get();
            return ResponseEntity.ok(newUser.isProfessor());
        }
        return null;
    }
}
