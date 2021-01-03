package com.gruppo13.CalendarMS.controllers;

import com.google.api.services.calendar.model.Event;
import com.gruppo13.CalendarMS.repositories.CalendarRepository;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    CalendarRepository calendarRepository;

    @GetMapping("/getAllEvents")
    public ResponseEntity<?> getAllEvents(){
        //return ResponseEntity.ok(calendarRepository.findAll());
        String email = "francilomuscio@gmail.com";
        String token = "ya29.a0AfH6SMAiejFDBgRXchTrs_PbKlov6UUkcYki1oH9XZNANGtliJYV66wC2un_VL-rxXUCWvEhc_Wt" +
                "q9GA84lTn0PF9u2jWILlT08jjFVb_8VUXznfkqnxNiES1PTU71S92N6imrDbze46mFmRhoKPC4l-dtGOxxL8dOAhNSoDK1A";
        String URI = "https://www.googleapis.com/calendar/v3/calendars/"+email+"/events";

        URL url = null;
        String response = new String();
        try {
            url = new URL(URI);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            return ResponseEntity.ok(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/addEvent")
    public ResponseEntity<String> addEvent(){
        try{
            Event event = new Event()
                    .setSummary("Google I/O 2015")
                    .setLocation("800 Howard St., San Francisco, CA 94103")
                    .setDescription("A chance to hear more about Google's developer products.");

            DateTime startDateTime = new DateTime("2015-05-28T09:00:00-07:00");
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("America/Los_Angeles");
            event.setStart(start);

            DateTime endDateTime = new DateTime("2015-05-28T17:00:00-07:00");
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("America/Los_Angeles");
            event.setEnd(end);

            String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
            event.setRecurrence(Arrays.asList(recurrence));

            EventAttendee[] attendees = new EventAttendee[] {
                    new EventAttendee().setEmail("lpage@example.com"),
                    new EventAttendee().setEmail("sbrin@example.com"),
            };
            event.setAttendees(Arrays.asList(attendees));

            EventReminder[] reminderOverrides = new EventReminder[] {
                    new EventReminder().setMethod("email").setMinutes(24 * 60),
                    new EventReminder().setMethod("popup").setMinutes(10),
            };
            Event.Reminders reminders = new Event.Reminders()
                    .setUseDefault(false)
                    .setOverrides(Arrays.asList(reminderOverrides));
            event.setReminders(reminders);

            String calendarId = "primary";
            event = service.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("false");
        }
    }

}
