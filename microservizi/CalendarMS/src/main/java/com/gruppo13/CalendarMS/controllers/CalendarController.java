package com.gruppo13.CalendarMS.controllers;

import com.google.gson.Gson;
import com.gruppo13.CalendarMS.models.Evento;
import com.gruppo13.CalendarMS.models.User;
import com.gruppo13.CalendarMS.repositories.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    CalendarRepository calendarRepository;


    @GetMapping("/getAllEvents")
    public ResponseEntity<?> getAllEvents(@RequestParam("user") String jsonObject) {
        Gson gson = new Gson();
        final User user = gson.fromJson(jsonObject, User.class);
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
