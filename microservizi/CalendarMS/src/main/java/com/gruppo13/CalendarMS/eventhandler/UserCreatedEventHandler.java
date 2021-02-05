package com.gruppo13.CalendarMS.eventhandler;


import com.gruppo13.CalendarMS.models.Student;
import com.gruppo13.CalendarMS.models.Teacher;
import com.gruppo13.CalendarMS.models.User;
import com.gruppo13.CalendarMS.repositories.StudentRepository;
import com.gruppo13.CalendarMS.repositories.TeacherRepository;
import com.gruppo13.CalendarMS.repositories.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Log4j2
@Component
@AllArgsConstructor
public class UserCreatedEventHandler {

    private UserRepository userRepo;
    private TeacherRepository teacherRepo;
    private StudentRepository studentRepo;

    @RabbitListener(queues = {"{queue.user-create}"})
    public void onUserCreated(@Payload String payload) {
        System.out.println("EVENTO: "+payload);
        JSONObject json = new JSONObject(payload);
        User toLoad = new User();
        toLoad.setId(Long.parseLong(json.get("id").toString()));
        toLoad.setName(json.getString("name"));
        toLoad.setSurname(json.getString("surname"));
        toLoad.setEmail(json.getString("email"));
        toLoad.setProfessor(json.getBoolean("isProfessor"));
        toLoad.setProvider(json.getString("provider"));
        //if(json.isNull("providerUserId"))
            ///toLoad.setProviderUserId(json.getString("providerUserId"));

        userRepo.saveAndFlush(toLoad);
        if(toLoad.isProfessor()){
            Teacher teacher = new Teacher();
            teacher.setId(Long.parseLong(json.get("id").toString()));
            teacher.setName(json.getString("name"));
            teacher.setSurname(json.getString("surname"));
            teacher.setEmail(json.getString("email"));
            teacher.setProvider(json.getString("provider"));
            //if(json.isNull("providerUserId"))
                //teacher.setProviderUserId(json.getString("providerUserId"));
            teacherRepo.saveAndFlush(teacher);
        }else{
            Student student = new Student();
            student.setId(Long.parseLong(json.get("id").toString()));
            student.setName(json.getString("name"));
            student.setSurname(json.getString("surname"));
            student.setEmail(json.getString("email"));
            student.setProvider(json.getString("provider"));
            //if(json.isNull("providerUserId"))
                //student.setProviderUserId(json.getString("providerUserId"));
            studentRepo.saveAndFlush(student);
        }
    }
}