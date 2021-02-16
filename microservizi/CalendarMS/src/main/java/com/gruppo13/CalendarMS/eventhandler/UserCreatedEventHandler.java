package com.gruppo13.CalendarMS.eventhandler;


import com.gruppo13.CalendarMS.models.Role;
import com.gruppo13.CalendarMS.models.Student;
import com.gruppo13.CalendarMS.models.Teacher;
import com.gruppo13.CalendarMS.models.User;
import com.gruppo13.CalendarMS.repositories.StudentRepository;
import com.gruppo13.CalendarMS.repositories.TeacherRepository;
import com.gruppo13.CalendarMS.repositories.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


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
        toLoad.setPassword(json.getString("password"));

        /*JSONArray rolesArray = json.getJSONArray("roles");
        Set<Role> roles = new HashSet<>();
        for(Object o : rolesArray){
            System.out.println(((String) o));
        }
        toLoad.setRoles(roles);*/
        if(!json.isNull("providerUserId"))
            toLoad.setProviderUserId(json.getString("providerUserId"));

        userRepo.saveAndFlush(toLoad);
        if(toLoad.isProfessor()){
            System.out.println("PROFESSORE");
            Teacher teacher = new Teacher();
            teacher.setId(toLoad.getId());
            teacher.setName(toLoad.getName());
            teacher.setSurname(toLoad.getSurname());
            teacher.setEmail(toLoad.getEmail());
            teacher.setProvider(toLoad.getProvider());
            teacher.setPassword(toLoad.getPassword());
            if(!json.isNull("providerUserId"))
                teacher.setProviderUserId(toLoad.getProviderUserId());
            teacherRepo.saveAndFlush(teacher);
        }else{
            System.out.println("STUDENTE");
            Student student = new Student();
            student.setId(toLoad.getId());
            student.setName(toLoad.getName());
            student.setSurname(toLoad.getSurname());
            student.setEmail(toLoad.getEmail());
            student.setProvider(toLoad.getProvider());
            student.setPassword(toLoad.getPassword());
            if(!json.isNull("providerUserId"))
                student.setProviderUserId(toLoad.getProviderUserId());
            studentRepo.saveAndFlush(student);
        }
    }
}