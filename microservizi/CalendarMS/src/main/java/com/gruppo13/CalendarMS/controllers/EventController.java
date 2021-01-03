package com.gruppo13.CalendarMS.controllers;

import com.gruppo13.CalendarMS.models.Event;
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
    public List<Event> getAllEvent() {
        List<Event> events = new ArrayList<Event>();
        eventRepo.findAll().forEach(events::add);

        return events;
    }

    @PostMapping(value = "/event/create")
    public Event postEvent(@RequestBody Event event) {
        Event _event = eventRepo.saveAndFlush(new Event(event.getStartTime()));

        return _event;
    }
}
