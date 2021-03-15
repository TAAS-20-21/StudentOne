package com.gruppo13.CalendarMS.eventhandler;

import com.gruppo13.CalendarMS.models.Course;
import com.gruppo13.CalendarMS.models.Student;
import com.gruppo13.CalendarMS.models.Teacher;
import com.gruppo13.CalendarMS.models.User;
import com.gruppo13.CalendarMS.repositories.CourseRepository;
import com.gruppo13.CalendarMS.repositories.StudentRepository;
import com.gruppo13.CalendarMS.repositories.TeacherRepository;
import com.gruppo13.CalendarMS.repositories.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@AllArgsConstructor
public class CourseCreatedEventHandler {

    private CourseRepository courseRepo;

    @RabbitListener(queues = {"{queue.course-create}"})
    public void onUserCreated(@Payload String payload) {
        System.out.println("EVENTO: "+payload);
        JSONObject json = new JSONObject(payload);
        Course toLoad = new Course();
        toLoad.setId(Long.parseLong(json.get("id").toString()));
        toLoad.setName(json.getString("name"));
        toLoad.setCFU(json.getInt("cfu"));
        toLoad.setLesson_hours(json.getInt("lessonHours"));

        if(!json.isNull("info"))
            toLoad.setInfo(json.getString("info"));

        courseRepo.saveAndFlush(toLoad);
    }
}