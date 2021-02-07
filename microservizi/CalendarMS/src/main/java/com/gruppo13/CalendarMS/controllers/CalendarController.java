package com.gruppo13.CalendarMS.controllers;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.google.gson.Gson;
import com.gruppo13.CalendarMS.config.CurrentUser;
import com.gruppo13.CalendarMS.dto.LocalUser;
import com.gruppo13.CalendarMS.models.CustomEvent;
import com.gruppo13.CalendarMS.models.User;
import com.gruppo13.CalendarMS.repositories.EventRepository;
import com.gruppo13.CalendarMS.repositories.StudentRepository;
import com.gruppo13.CalendarMS.repositories.WorkingGroupRepository;
import com.gruppo13.CalendarMS.util.ModifierObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

import com.gruppo13.CalendarMS.calendar.CalendarFromTokenCreator;
import com.gruppo13.CalendarMS.util.EventObject;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    EventRepository eventRepo;

    //costanti utilizzate per definire il range del GoogleId generato al momento della creazione di un evento
    Integer MIN = 0;
    Integer MAX = Integer.MAX_VALUE;
    Integer MILLISECONDS_IN_DAY = 86400000;

    @Autowired
    StudentRepository studentRepo;

    @Autowired
    WorkingGroupRepository wkRepo;

    // public ResponseEntity<?> getAllEvents(, @RequestHeader("Authorization")String token)

    @GetMapping("/getAllEvents")
    public ResponseEntity<?> getAllEvents(@CurrentUser LocalUser user, @RequestHeader("Authorization") String token){
        try{
            List<Long> courseList = studentRepo.getCourseIdByStudent(user.getUser().getId());
            List<Long> workingGroupList = wkRepo.getGroupIdByStudent(user.getUser().getId());
            List<CustomEvent> eventList = new ArrayList<CustomEvent>();

            //prelievo degli eventi di tipo lesson
            for(Long id_course:courseList){
                eventList.addAll(eventRepo.findByCourseId(id_course));
            }

            //prelievo degli eventi di tipo lesson
            for(Long id_group:workingGroupList){
                eventList.addAll(eventRepo.findByWorkingGroupId(id_group));
            }

            if(user.getUser().getProvider().equals("google")) {
                //richiamo della sincronizzazione con Google per tutti gli eventi prelevati con i due statement for precedenti
                for (CustomEvent event : eventList) {
                    synchWithGoogle(event, token);
                }
                Calendar service = new CalendarFromTokenCreator().getService(token);
                Events events = service.events().list("primary").execute();
                List<Event> items = events.getItems();
                for (Event el : items) {
                    if (!eventRepo.existsByGoogleId(el.getId())) {
                        service.events().delete("primary", el.getId()).execute();
                    }
                }
            }

            return ResponseEntity.ok(eventList.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping(value = "/addEvent", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustomEvent> addEvent(@RequestBody EventObject paramEvent, @RequestHeader("Authorization") String token) {

        String summary = new String();
        String location = new String();
        String description = new String();
        DateTime startDateTime;
        DateTime endDateTime;
        String id = new String("studentone");
        String number = Integer.toString(Math.abs(MIN + (int) (Math.random() * ((MAX - MIN) + 1))));
        id += number;
        try {
            //setting dei valori richiesti per la creazione di un evento mediante Google Calendar API
            summary = paramEvent.getSummary();
            location = paramEvent.getLocation();
            description = paramEvent.getDescription();
            startDateTime = paramEvent.getStartDateTime();
            endDateTime = paramEvent.getEndDateTime();

            //setting degli eventi per memorizzazione nel db
            CustomEvent _event = new CustomEvent();
            _event.setGoogleId(id);
            _event.setTitle(summary);
            _event.setType(paramEvent.getType());
            _event.setAngularId(paramEvent.getAngularId());

            //parametri necessari anche per gli eventi ricorrenti(per gestire appunto gli eventi ricorrenti su Google Calendar)
            _event.setStartTime(new Date(startDateTime.getValue()));
            _event.setEndTime(new Date(endDateTime.getValue()));
            if(paramEvent.getStartRecur() != null) {
                _event.setStartRecur(paramEvent.getStartRecur());
                _event.setEndRecur(paramEvent.getEndRecur());

                _event.setDaysOfWeek(paramEvent.getDaysOfWeek());
                _event.setStartTimeRecurrent(paramEvent.getStartTimeRecurrent());
                _event.setEndTimeRecurrent(paramEvent.getEndTimeRecurrent());

            }

            if (paramEvent.getCourse() != null)
                _event.setCourse(paramEvent.getCourse());
            else {
                _event.setWorkingGroup(paramEvent.getWorkingGroup());
            }

            //setting degli eventi per memorizzazione su Google Calendar
            Calendar service = new CalendarFromTokenCreator().getService(token);

            Event event = new Event()
                    .setId(id)
                    .setSummary(summary)
                    .setLocation(location)
                    .setDescription(description);

            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime);
            start.setTimeZone("+01:00");
            event.setStart(start);

            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime);
            end.setTimeZone("+01:00");
            event.setEnd(end);


            if(paramEvent.getStartRecur() != null){
                String dateTmp = new DateTime(paramEvent.getEndRecur()).toString();
                dateTmp = dateTmp.replaceAll("-", "");
                dateTmp = dateTmp.replaceAll(":", "");
                dateTmp = dateTmp.substring(0, 15);

                String daysOfWeek = paramEvent.getDaysOfWeek();
                String[] daysArray = daysOfWeek.split("");

                //questa funzione traduce gli indici dei giorni(usati nella libreria angular di fullcalendar) in sigle dei giorni della settimana
                // (necessari per la memorizzazione di eventi ricorrenti su Google Calendar
                String days = translateDaysIndex(daysArray);
                event.setRecurrence(Arrays.asList("RRULE:FREQ=WEEKLY;UNTIL=" + dateTmp + "Z;BYDAY=" + days.substring(0, days.length() - 1)));
            }

            String calendarId = "primary";

            event = service.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());

            eventRepo.saveAndFlush(_event);

            return ResponseEntity.ok(_event);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @PostMapping(value = "/modify/time", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<CustomEvent>> modifyTime(@RequestBody ModifierObject obj,@RequestHeader("Authorization") String token){
        Optional<CustomEvent> event = eventRepo.findById(obj.getId());
        CustomEvent newEvent;
        List<CustomEvent> newEventsCreated = new ArrayList<CustomEvent>();
        if(event != null){
            newEvent = event.get();
            if(newEvent.getStartRecur() == null) {
                if (obj.getEndDate() != null)
                    newEvent.setEndTime(obj.getEndDate());
                if (obj.getStartDate() != null)
                    newEvent.setStartTime(obj.getStartDate());

                try {
                    Calendar service = new CalendarFromTokenCreator().getService(token);
                    Event _event = service.events().get("primary", newEvent.getGoogleId()).execute();
                    service.events().delete("primary", _event.getId()).execute();


                    String id = new String("studentone");
                    String number = Integer.toString(Math.abs(MIN + (int) (Math.random() * ((MAX - MIN) + 1))));
                    id += number;
                    newEvent.setGoogleId(id);
                    Event temp = new Event()
                            .setId(id)
                            .setSummary(newEvent.getTitle())
                            .setLocation("")
                            .setDescription("");
                    ;
                    EventDateTime start = new EventDateTime()
                            .setDateTime(new DateTime(newEvent.getStartTime()));
                    EventDateTime end = new EventDateTime()
                            .setDateTime(new DateTime(newEvent.getEndTime()));
                    temp.setStart(start);
                    temp.setEnd(end);

                    Event updatedEvent = service.events().insert("primary", temp).execute();
                    eventRepo.saveAndFlush(newEvent);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            else{
                EventObject preEvent = new EventObject();
                EventObject singleEvent =  new EventObject();
                EventObject postEvent =  new EventObject();
                Long maxAngularId = eventRepo.maxAngularId();

                //incremento necessario affinchè sia garantita l'univocità dell'angular Id per gli eventi ricorrenti che precedono
                //l'evento che è stato modificato nella vecchia ricorrenza
                maxAngularId += 1;
                preEvent = setPreEventRecur(newEvent, obj, maxAngularId);

                maxAngularId += 1;
                postEvent = setPostEventRecur(newEvent, obj, maxAngularId);

                maxAngularId += 1;
                singleEvent = setSingleEventRecur(newEvent, obj, maxAngularId);

                Calendar service = null;
                try {
                    service = new CalendarFromTokenCreator().getService(token);
                    Event _event = service.events().get("primary", newEvent.getGoogleId()).execute();
                    service.events().delete("primary", newEvent.getGoogleId()).execute();
                    eventRepo.deleteById(newEvent.getId());

                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                newEventsCreated.add(this.addEvent(preEvent, token).getBody());
                newEventsCreated.add(this.addEvent(singleEvent, token).getBody());
                newEventsCreated.add(this.addEvent(postEvent, token).getBody());

            }
        }
        return ResponseEntity.ok(newEventsCreated);
    }

    private EventObject setSingleEventRecur(CustomEvent newEvent, ModifierObject obj, Long angularId) {

        EventObject singleEvent = new EventObject();;
        singleEvent.setSummary(newEvent.getTitle());
        singleEvent.setStartDateTime(new DateTime(obj.getStartDate()));
        singleEvent.setEndDateTime(new DateTime(obj.getEndDate()));

        singleEvent.setAngularId(angularId);
        singleEvent.setCourse(newEvent.getCourse());
        singleEvent.setWorkingGroup(newEvent.getWorkingGroup());
        singleEvent.setType(newEvent.getType());

        return singleEvent;
    }


    @PostMapping(value ="/modify/title", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> modifyTitle(@RequestBody ModifierObject obj, @RequestHeader("Authorization") String token){
        Optional<CustomEvent> event = eventRepo.findById(obj.getId());
        CustomEvent newEvent;
        if(event != null){
            newEvent = event.get();
            newEvent.setTitle(obj.getStr());
            //eventRepo.saveAndFlush(newEvent);

            try {
                Calendar service = new CalendarFromTokenCreator().getService(token);
                Event _event = service.events().get("primary", newEvent.getGoogleId()).execute();

                service.events().delete("primary", _event.getId()).execute();


                String id = new String("studentone");
                String number = Integer.toString(Math.abs(MIN + (int) (Math.random() * ((MAX - MIN) + 1))));
                id += number;
                newEvent.setGoogleId(id);
                Event temp = new Event()
                        .setId(id)
                        .setSummary(newEvent.getTitle())
                        .setLocation("")
                        .setDescription("");

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


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/deleteEvent", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteEvent(@RequestBody ModifierObject obj, @RequestHeader("Authorization") String token){
        Optional<CustomEvent> event = eventRepo.findById(obj.getId());
        CustomEvent newEvent;
        if(event != null) {
            newEvent = event.get();


            try {
                Calendar service = new CalendarFromTokenCreator().getService(token);
                service.events().delete("primary", newEvent.getGoogleId()).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            eventRepo.deleteById(obj.getId());
            return ResponseEntity.ok(newEvent.getGoogleId());
        }
        return ResponseEntity.ok("ok");
    }


    @PostMapping(value = "/findByAngularId", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> findByAngularId(@RequestBody ModifierObject obj) {
        CustomEvent event = eventRepo.findByAngularId(obj.getId());
        return ResponseEntity.ok(event);
    }




    //_________________________FUNZIONI DI SUPPORTO______________________________________________
    private void synchWithGoogle(CustomEvent paramEvent, String token) {
        String summary = new String();
        String location = new String();
        String description = new String();
        DateTime startDateTime;
        DateTime endDateTime;
        String id = new String("studentone");

        String number = Integer.toString(Math.abs(MIN + (int) (Math.random() * ((MAX - MIN) + 1))));
        id += number;

        try {
            Calendar service = new CalendarFromTokenCreator().getService(token);
            Event _event = service.events().get("primary", paramEvent.getGoogleId()).execute();

        }catch(GoogleJsonResponseException e){

            //nel caso in cui un evento E non dovesse essere presente in Google Calendar, è necessario gestire
            // questa mancanza aggiungendo appunto tale evento. La chiamata alla riga 375, nel caso in cui E non viene trovato,
            // genera un'eccezione che viene gestita quindi dal codice che segue
            if(e.getMessage().startsWith("404 Not Found")){
                try {
                    Calendar service = new CalendarFromTokenCreator().getService(token);
                    summary = paramEvent.getTitle();
                    startDateTime = new DateTime(paramEvent.getStartTime());
                    endDateTime = new DateTime(paramEvent.getEndTime());
                    Event event = new Event()
                            .setId(paramEvent.getGoogleId())
                            .setSummary(summary)
                            .setLocation(location)
                            .setDescription(description);

                    EventDateTime start = new EventDateTime()
                            .setDateTime(startDateTime);
                    start.setTimeZone("+01:00");
                    event.setStart(start);

                    EventDateTime end = new EventDateTime()
                            .setDateTime(endDateTime);
                    end.setTimeZone("+01:00");
                    event.setEnd(end);

                    if(paramEvent.getStartRecur() != null){
                        String dateTmp = new DateTime(paramEvent.getEndRecur()).toString();
                        dateTmp = dateTmp.replaceAll("-", "");
                        dateTmp = dateTmp.replaceAll(":", "");
                        dateTmp = dateTmp.substring(0, 15);

                        String daysOfWeek = paramEvent.getDaysOfWeek();
                        String[] daysArray = daysOfWeek.split("");
                        String days = translateDaysIndex(daysArray);

                        event.setRecurrence(Arrays.asList("RRULE:FREQ=WEEKLY;UNTIL=" + dateTmp + "Z;BYDAY=" + days.substring(0, days.length() - 1)));
                    }

                    String calendarId = "primary";
                    event = service.events().insert(calendarId, event).execute();
                    System.out.printf("Event created: %s\n", event.getHtmlLink());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String translateDaysIndex(String[] daysArray){
        String days = "";
        for(int i = 0; i < daysArray.length; i++){
            switch(daysArray[i]){
                case "0":
                    days += "SU,";
                    break;
                case "1":
                    days += "MO,";
                    break;
                case "2":
                    days += "TU,";
                    break;
                case "3":
                    days += "WE,";
                    break;
                case "4":
                    days += "TH,";
                    break;
                case "5":
                    days += "FR,";
                    break;
                case "6":
                    days += "SA,";
                    break;
            }
        }
        return days;
    }

    private EventObject setPostEventRecur(CustomEvent newEvent, ModifierObject obj, Long angularId) {
        EventObject postEvent =  new EventObject();

        postEvent.setSummary(newEvent.getTitle());

        //la ricorrenza del secondo sotto-intervallo, inizia il giorno dopo in cui si TENEVA l'evento nella ricorrenza modificato
        Date startRecur = new Date(obj.getOldEndDate().getTime() + MILLISECONDS_IN_DAY);
        Date endRecur = newEvent.getEndRecur();
        if(startRecur.before(endRecur)) {
            postEvent.setStartRecur(startRecur);
            postEvent.setEndRecur(endRecur);
        }
        else {
            postEvent.setStartRecur(startRecur);
            postEvent.setEndRecur(startRecur);
        }
        postEvent.setStartTimeRecurrent(newEvent.getStartTimeRecurrent());
        postEvent.setEndTimeRecurrent(newEvent.getEndTimeRecurrent());
        postEvent.setDaysOfWeek(newEvent.getDaysOfWeek());

        postEvent.setAngularId(angularId);
        postEvent.setCourse(newEvent.getCourse());
        postEvent.setWorkingGroup(newEvent.getWorkingGroup());
        postEvent.setType(newEvent.getType());

        List<String> daysOfWeekNames = this.setNewStartRecurrence(newEvent.getDaysOfWeek(),obj.getOldEndDate(), obj.getOldStartDate());

        //istruzione utile per ricavare la data seguente alla vecchia data in cui si teneva l'evento in corso di modifica
        Date temp = new Date(obj.getOldStartDate().getTime() + (MILLISECONDS_IN_DAY));
        String tempAsString = temp.toString().substring(0,3);
        //postEvent.setStartDateTime(new DateTime(temp));
        //postEvent.setEndDateTime(new DateTime(temp.getTime() + (newEvent.getEndTimeRecurrent() - newEvent.getStartTimeRecurrent())));

        //il codice che segue è utile per determinare la data a partire dalla quale deve cominciare la ricorrenza di postEvent
        while(true){
            if(temp.equals(postEvent.getEndRecur()))
                break;

            if(daysOfWeekNames.contains(tempAsString)){
                postEvent.setStartDateTime(new DateTime(temp));
                postEvent.setEndDateTime(new DateTime(temp.getTime() + (newEvent.getEndTimeRecurrent() - newEvent.getStartTimeRecurrent())));
                break;
            }
            temp = new Date(temp.getTime() + (MILLISECONDS_IN_DAY));
            tempAsString = temp.toString().substring(0,3);
        }

        return postEvent;
    }

    private EventObject setPreEventRecur(CustomEvent newEvent, ModifierObject obj, Long angularId) {
        EventObject preEvent  = new EventObject();


        preEvent.setSummary(newEvent.getTitle());


        //settaggio dell'intervallo di ricorrenti che precede l'evento nella ricorrenza che si vuole modificare
        //la ricorrenza del primo sotto-intervallo, termina il giorno prima in cui si TENEVA l'evento nella ricorrenza modificato
        Date startRecur = newEvent.getStartRecur();
        Date endRecur = new Date(obj.getOldStartDate().getTime() - (MILLISECONDS_IN_DAY));
        if(startRecur.before(endRecur)) {
            preEvent.setStartRecur(startRecur);
            preEvent.setEndRecur(endRecur);
        }
        else {
            preEvent.setStartRecur(startRecur);
            preEvent.setEndRecur(startRecur);
        }

        preEvent.setStartTimeRecurrent(newEvent.getStartTimeRecurrent());
        preEvent.setEndTimeRecurrent(newEvent.getEndTimeRecurrent());
        preEvent.setDaysOfWeek(newEvent.getDaysOfWeek());
        preEvent.setStartDateTime(new DateTime(newEvent.getStartTime()));
        preEvent.setEndDateTime(new DateTime(newEvent.getEndTime()));

        preEvent.setAngularId(angularId);
        preEvent.setCourse(newEvent.getCourse());
        preEvent.setWorkingGroup(newEvent.getWorkingGroup());
        preEvent.setType(newEvent.getType());

        return preEvent;
    }

    private List<String> setNewStartRecurrence(String daysOfWeek, Date oldEndDate, Date oldStartDate) {
        String[] daysAsArray = daysOfWeek.split("");
        List<String> daysNames = new ArrayList<String>();
        for(int i = 0; i < daysAsArray.length; i++){
            switch(daysAsArray[i]){
                case "0":
                    daysNames.add("Sun");
                    break;
                case "1":
                    daysNames.add("Mon");
                    break;
                case "2":
                    daysNames.add("Tue");
                    break;
                case "3":
                    daysNames.add("Wed");
                    break;
                case "4":
                    daysNames.add("Thu");
                    break;
                case "5":
                    daysNames.add("Fri");
                    break;
                case "6":
                    daysNames.add("Sat");
                    break;
            }
        }
        return daysNames;
    }
}
