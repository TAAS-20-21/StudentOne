package com.gruppo13.CalendarMS.controllers;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.calendar.model.EventDateTime;
import com.gruppo13.CalendarMS.models.CustomEvent;
import com.gruppo13.CalendarMS.repositories.CalendarRepository;
import com.gruppo13.CalendarMS.repositories.EventRepository;
import com.gruppo13.CalendarMS.repositories.StudentRepository;
import com.gruppo13.CalendarMS.repositories.WorkingGroupRepository;
import com.gruppo13.CalendarMS.util.ModifierObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.gruppo13.CalendarMS.calendar.CalendarFromTokenCreator;
import com.gruppo13.CalendarMS.util.EventObject;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    EventRepository eventRepo;

    Integer MIN = 0;
    Integer MAX = 1000;

    @Autowired
    StudentRepository studentRepo;

    @Autowired
    WorkingGroupRepository wkRepo;

    @GetMapping("/getAllEvents")
    public ResponseEntity<?> getAllEvents(){
        try{
            List<Long> courseList = studentRepo.getCourseIdByStudent(1L);
            List<Long> workingGroupList = wkRepo.getGroupIdByStudent(1L);
            List<CustomEvent> eventList = new ArrayList<CustomEvent>();
            for(Long id_course:courseList){
                eventList.addAll(eventRepo.findByCourseId(id_course));
            }

            for(Long id_group:workingGroupList){
                eventList.addAll(eventRepo.findByWorkingGroupId(id_group));
            }

            for(CustomEvent event:eventList){
                synchWithGoogle(event);
            }


            /*
            DA AGGIUNGERE QUANDO CI SARANNO PIU' UTENTI
            Calendar service = new CalendarFromTokenCreator().getService();
            Events events = service.events().list("primary").execute();
            List<Event> items = events.getItems();
            for(Event el: items){
                if(!eventRepo.existsByGoogleId(el.getId())){
                    service.events().delete("primary", el.getId()).execute();
                }
            }
            */



            return ResponseEntity.ok(eventList.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/addEvent", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addEvent(@RequestBody EventObject paramEvent) {
        String summary = new String();
        String location = new String();
        String description = new String();
        DateTime startDateTime;
        DateTime endDateTime;
        String id = new String("studentone");

        id += Integer.toString(MIN + (int) (Math.random() * ((MAX - MIN) + 1)));

        try {
            summary = paramEvent.getSummary();
            location = paramEvent.getLocation();
            description = paramEvent.getDescription();
            startDateTime = paramEvent.getStartDateTime();
            endDateTime = paramEvent.getEndDateTime();

            Calendar service = new CalendarFromTokenCreator().getService();

            Event event = new Event()
                    .setId(id)
                    .setSummary(summary)
                    .setLocation(location)
                    .setDescription(description);

            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime);
            event.setStart(start);

            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime);
            event.setEnd(end);

            String calendarId = "primary";
            event = service.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());

            CustomEvent _event = new CustomEvent();
            _event.setGoogleId(id);
            _event.setTitle(summary);
            _event.setStartTime(new Date(startDateTime.getValue()));
            _event.setEndTime(new Date(endDateTime.getValue()));
            _event.setType(paramEvent.getType());
            if (paramEvent.getCourse() != null)
                _event.setCourse(paramEvent.getCourse());
            else {
                _event.setWorkingGroup(paramEvent.getWorkingGroup());
            }
            eventRepo.saveAndFlush(_event);

            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(value = "/modify/end_time", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> modifyEndTime(@RequestBody ModifierObject obj){
        Optional<CustomEvent> event = eventRepo.findById(obj.getId());
        CustomEvent newEvent;
        if(event != null){
            newEvent = event.get();
            newEvent.setEndTime(obj.getDate());
            //eventRepo.saveAndFlush(newEvent);

           try {
                Calendar service = new CalendarFromTokenCreator().getService();
                Event _event = service.events().get("primary", newEvent.getGoogleId()).execute();

                service.events().delete("primary", _event.getId()).execute();


                String id = new String("studentone");
                id += Integer.toString(MIN + (int) (Math.random() * ((MAX - MIN) + 1)));
                newEvent.setGoogleId(id);
                Event temp = new Event()
                        .setId(id)
                        .setSummary(newEvent.getTitle())
                        .setLocation("")
                        .setDescription("");;

                EventDateTime start = new EventDateTime()
                       .setDateTime(new DateTime(newEvent.getStartTime()));
                EventDateTime end = new EventDateTime()
                       .setDateTime(new DateTime(obj.getDate()));
                temp.setStart(start);
                temp.setEnd(end);

                Event updatedEvent = service.events().insert("primary", temp).execute();
                eventRepo.saveAndFlush(newEvent);
               return ResponseEntity.ok(id);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return ResponseEntity.ok("ok");
    }

    @PostMapping(value = "/modify/start_time", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> modifyStartTime(@RequestBody ModifierObject obj){
        Optional<CustomEvent> event = eventRepo.findById(obj.getId());
        CustomEvent newEvent;
        if(event != null){
            newEvent = event.get();
            newEvent.setStartTime(obj.getDate());
            //eventRepo.saveAndFlush(newEvent);

            try {
                Calendar service = new CalendarFromTokenCreator().getService();
                Event _event = service.events().get("primary", newEvent.getGoogleId()).execute();

                service.events().delete("primary", _event.getId()).execute();


                String id = new String("studentone");
                id += Integer.toString(MIN + (int) (Math.random() * ((MAX - MIN) + 1)));
                newEvent.setGoogleId(id);
                Event temp = new Event()
                        .setId(id)
                        .setSummary(newEvent.getTitle())
                        .setLocation("")
                        .setDescription("");;

                EventDateTime start = new EventDateTime()
                        .setDateTime(new DateTime(obj.getDate()));
                EventDateTime end = new EventDateTime()
                        .setDateTime(new DateTime(newEvent.getEndTime()));
                temp.setStart(start);
                temp.setEnd(end);

                Event updatedEvent = service.events().insert("primary", temp).execute();
                eventRepo.saveAndFlush(newEvent);
                return ResponseEntity.ok(id);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return ResponseEntity.ok("ok");
    }

    @PostMapping(value ="/modify/title", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> modifyTitle(@RequestBody ModifierObject obj){
        Optional<CustomEvent> event = eventRepo.findById(obj.getId());
        CustomEvent newEvent;
        if(event != null){
            newEvent = event.get();
            newEvent.setTitle(obj.getStr());
            //eventRepo.saveAndFlush(newEvent);

            try {
                Calendar service = new CalendarFromTokenCreator().getService();
                Event _event = service.events().get("primary", newEvent.getGoogleId()).execute();

                service.events().delete("primary", _event.getId()).execute();


                String id = new String("studentone");
                id += Integer.toString(MIN + (int) (Math.random() * ((MAX - MIN) + 1)));
                newEvent.setGoogleId(id);
                Event temp = new Event()
                        .setId(id)
                        .setSummary(newEvent.getTitle())
                        .setLocation("")
                        .setDescription("");;

                EventDateTime start = new EventDateTime()
                        .setDateTime(new DateTime(newEvent.getStartTime()));
                EventDateTime end = new EventDateTime()
                        .setDateTime(new DateTime(newEvent.getEndTime()));
                temp.setStart(start);
                temp.setEnd(end);

                Event updatedEvent = service.events().insert("primary", temp).execute();
                eventRepo.saveAndFlush(newEvent);
                return ResponseEntity.ok(id);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return ResponseEntity.ok("ok");
    }

    @PostMapping(value = "/modify/event_type", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> modifyEventType(@RequestBody ModifierObject obj){
        Optional<CustomEvent> event = eventRepo.findById(obj.getId());
        CustomEvent newEvent;
        if(event != null) {
            newEvent = event.get();
            newEvent.setType(obj.getEventType());
            eventRepo.saveAndFlush(newEvent);
            return ResponseEntity.ok(newEvent.getGoogleId());
        }
        return null;
    }

    @PostMapping(value = "/deleteEvent", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteEvent(@RequestBody ModifierObject obj){
        Optional<CustomEvent> event = eventRepo.findById(obj.getId());
        CustomEvent newEvent;
        if(event != null) {
            newEvent = event.get();


            try {
                Calendar service = new CalendarFromTokenCreator().getService();
                service.events().delete("primary", newEvent.getGoogleId()).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            eventRepo.deleteById(obj.getId());
            return ResponseEntity.ok(newEvent.getGoogleId());
        }
        return ResponseEntity.ok("ok");
    }

    private void synchWithGoogle(CustomEvent paramEvent) {
        String summary = new String();
        String location = new String();
        String description = new String();
        DateTime startDateTime;
        DateTime endDateTime;
        String id = new String("studentone");

        id += Integer.toString(MIN + (int) (Math.random() * ((MAX - MIN) + 1)));

        try {
            summary = paramEvent.getTitle();
            startDateTime = new DateTime(paramEvent.getStartTime());
            endDateTime = new DateTime(paramEvent.getEndTime());

            Calendar service = new CalendarFromTokenCreator().getService();

            Event event = new Event()
                    .setId(id)
                    .setSummary(summary)
                    .setLocation(location)
                    .setDescription(description);

            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime);
            event.setStart(start);

            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime);
            event.setEnd(end);

            String calendarId = "primary";
            event = service.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());
            eventRepo.saveAndFlush(paramEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
