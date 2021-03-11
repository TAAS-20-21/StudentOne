package com.gruppo13.CalendarMS.controllers;

import com.gruppo13.CalendarMS.models.User;
import com.gruppo13.CalendarMS.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
