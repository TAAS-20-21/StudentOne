package com.gruppo13.CalendarMS.controllers;

import com.gruppo13.CalendarMS.models.Evento;
import com.gruppo13.CalendarMS.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    EventRepository eventRepo;

    @GetMapping("/event")
    public List<Evento> getAllEvent() {
        List<Evento> events = new ArrayList<Evento>();
        eventRepo.findAll().forEach(events::add);

        return events;
    }

    @PostMapping(value = "/event/create")
    public Evento postEvent(@RequestBody Evento event) {
        Evento _event = eventRepo.saveAndFlush(new Evento(event.getStartTime()));

        return _event;
    }
}
