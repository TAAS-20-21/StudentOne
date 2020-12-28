package com.gruppo13.CalendarMS.controllers;

import com.gruppo13.CalendarMS.models.Evento;
import com.gruppo13.CalendarMS.repositories.CalendarRepository;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    CalendarRepository calendarRepository;

    @GetMapping("/getAllEvents")
    public ResponseEntity<?> getAllEvents(){
        //return ResponseEntity.ok(calendarRepository.findAll());
        String email = "francilomuscio@gmail.com";
        String token = "ya29.a0AfH6SMCuEY_Qd2S4qCKpTJ1fRcCq-H9yC6V7FHL2iiMHLzXSkox2u8Tv0caZedxocPJLsphpLpGnmy4k_QnI_WTcbA7J3ATlyz-S-bSCz2EWUh-Y1dLadPHUVMUvQIXQtGukIviiixIRaKBSLHUdedTGHNLxT6knSUjp4zxvm0g";
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
            //JSONObject json = new JSONObject(sb);
            //JSONArray jsonArr = new JSONArray(sb.toString());
            //return ResponseEntity.ok(jsonArr);
            return ResponseEntity.ok(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
