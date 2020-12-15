package com.gruppo13.CalendarMS.controllers;

import com.gruppo13.CalendarMS.models.Evento;
import com.gruppo13.CalendarMS.repositories.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    CalendarRepository calendarRepository;

    @GetMapping("/getAllEvents")
    public ResponseEntity<?> getAllEvents(){
        return ResponseEntity.ok(calendarRepository.findAll());
    }

    @PostMapping("/addEvent")
    public ResponseEntity<Boolean> addEvento(@RequestBody Evento evento){
        try{
            calendarRepository.saveAndFlush(evento);
            return ResponseEntity.ok(true);
        } catch (Exception e){
            return ResponseEntity.ok(false);
        }
    }

}
