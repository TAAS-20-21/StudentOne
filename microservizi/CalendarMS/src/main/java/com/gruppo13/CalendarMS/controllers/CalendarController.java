package com.gruppo13.CalendarMS.controllers;

import com.gruppo13.CalendarMS.models.Event;
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
        String token = "ya29.a0AfH6SMBZQZiuVDSvqkr1fCqTHSrG_FDqfu6OtDl6xy99CoI2S527FUH-ycp5-Igs6GP9H2kU3LBYWfq0ZaWtt7jkpErZaDcPOaHt4QfoLY8mzduNGqPCQ9APvSzE_OWPUd6uCfQDK3vxSTX0jkYVYx2Vo0-06UoSUs7SjSE6OgE";
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
    public ResponseEntity<Boolean> addEvent(){
        try{
            String email = "francilomuscio@gmail.com";
            String token = "ya29.a0AfH6SMBZQZiuVDSvqkr1fCqTHSrG_FDqfu6OtDl6xy99CoI2S527FUH-ycp5-Igs6GP9H2kU3LBYWfq0ZaWtt7jkpErZaDcPOaHt4QfoLY8mzduNGqPCQ9APvSzE_OWPUd6uCfQDK3vxSTX0jkYVYx2Vo0-06UoSUs7SjSE6OgE";
            String URI = "https://www.googleapis.com/calendar/v3/calendars/" + email + "/events";
            URL url = null;
            String response = new String();
            url = new URL(URI);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            String message;
            JSONObject json = new JSONObject();
            JSONObject json_end = new JSONObject();
            JSONObject json_start = new JSONObject();

            json_end.put("dateTime", "2021-01-20T17:00:00+01:00");
            json_start.put("dateTime", "2021-01-20T09:00:00+01:00");
            json.put("end", json_end);
            json.put("start", json_start);
            json.put("summary", "Test");

            OutputStream os = connection.getOutputStream();
            os.write(json.toString().getBytes("UTF-8"));
            os.close();


            //request.setEntity(new UrlEncodedFormEntity(pairs ));
            //calendarRepository.saveAndFlush(event);
            return ResponseEntity.ok(true);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(false);
        }
    }

}
