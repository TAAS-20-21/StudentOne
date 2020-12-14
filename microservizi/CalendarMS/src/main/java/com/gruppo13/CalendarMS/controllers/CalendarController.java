package com.gruppo13.CalendarMS.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Calendar")
public class CalendarController {

    @GetMapping("/getAllEvents")
    public ResponseEntity<?> getAllEvents(){
        return null;
    }
}
