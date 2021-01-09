package com.gruppo13.CalendarMS.controllers;

import com.gruppo13.CalendarMS.models.CustomEvent;
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
    public List<CustomEvent> getAllEvent() {
        List<CustomEvent> customEvents = new ArrayList<CustomEvent>();
        eventRepo.findAll().forEach(customEvents::add);

        return customEvents;
    }

    @PostMapping(value = "/event/create")
    public CustomEvent postEvent(@RequestBody CustomEvent customEvent) {
        CustomEvent _Custom_event = eventRepo.saveAndFlush(new CustomEvent(customEvent));

        return _Custom_event;
    }
}
