package com.gruppo13.CoursesMS.eventhandler;


import com.gruppo13.CoursesMS.CoursesMsApplication;
import com.gruppo13.CoursesMS.model.Student;
import com.gruppo13.CoursesMS.model.Teacher;
import com.gruppo13.CoursesMS.model.User;
import com.gruppo13.CoursesMS.repository.StudentRepository;
import com.gruppo13.CoursesMS.repository.TeacherRepository;
import com.gruppo13.CoursesMS.repository.UserRepository;
import com.gruppo13.CoursesMS.service.UserServiceSAGA;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Log4j2
@Component
@AllArgsConstructor
public class UserCreatedEventHandler {

    private UserRepository userRepo;
    private TeacherRepository teacherRepo;
    private StudentRepository studentRepo;

    @Autowired
    private UserServiceSAGA service;

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

        if(!json.isNull("providerUserId"))
            toLoad.setProviderUserId(json.getString("providerUserId"));
        if(!userRepo.existsById(toLoad.getId())) {
            userRepo.saveAndFlush(toLoad);
            if (toLoad.isProfessor()) {
                System.out.println("PROFESSORE");
                Teacher teacher = new Teacher();
                teacher.setId(toLoad.getId());
                teacher.setName(toLoad.getName());
                teacher.setSurname(toLoad.getSurname());
                teacher.setEmail(toLoad.getEmail());
                teacher.setProvider(toLoad.getProvider());
                teacher.setPassword(toLoad.getPassword());
                if (!json.isNull("providerUserId"))
                    teacher.setProviderUserId(toLoad.getProviderUserId());
                teacherRepo.saveAndFlush(teacher);
            } else {
                System.out.println("STUDENTE");
                Student student = new Student();
                student.setId(toLoad.getId());
                student.setName(toLoad.getName());
                student.setSurname(toLoad.getSurname());
                student.setEmail(toLoad.getEmail());
                student.setProvider(toLoad.getProvider());
                student.setPassword(toLoad.getPassword());
                if (!json.isNull("providerUserId"))
                    student.setProviderUserId(toLoad.getProviderUserId());
                studentRepo.saveAndFlush(student);
            }

            service.createUser(toLoad);
        }
    }
}