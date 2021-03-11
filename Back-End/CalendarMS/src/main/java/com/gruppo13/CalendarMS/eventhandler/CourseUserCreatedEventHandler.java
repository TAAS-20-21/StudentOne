package com.gruppo13.CalendarMS.eventhandler;

import com.gruppo13.CalendarMS.models.Course;
import com.gruppo13.CalendarMS.repositories.StudentRepository;
import com.gruppo13.CalendarMS.repositories.TeacherRepository;
import com.gruppo13.CalendarMS.repositories.UserRepository;
import com.gruppo13.CalendarMS.service.CourseUserServiceSAGA;
import com.gruppo13.CalendarMS.util.CourseUserRelObject;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@AllArgsConstructor
public class CourseUserCreatedEventHandler {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private TeacherRepository teacherRepo;

    @Autowired
    private CourseUserServiceSAGA service;

    @RabbitListener(queues = {"{queue.course-user-create}"})
    public void onCourseUserCreated(@Payload String payload) {
        System.out.println("EVENTO: "+payload);
        JSONObject json = new JSONObject(payload);
        CourseUserRelObject toLoad = new CourseUserRelObject();
        toLoad.setCourseId(Long.parseLong(json.get("courseId").toString()));
        toLoad.setPersonId(Long.parseLong(json.get("userId").toString()));
        if(teacherRepo.existsById(toLoad.getPersonId())){
            if(!teacherRepo.getCourseIdByTeacher(toLoad.getPersonId()).contains(toLoad.getCourseId())) {
                teacherRepo.addAssignedCourse(toLoad.getCourseId(), toLoad.getPersonId());
                service.createCourseUser(toLoad);
            }
        }else{
            if(!studentRepo.getCourseIdByStudent(toLoad.getPersonId()).contains(toLoad.getCourseId())) {
                studentRepo.addLikedCourse(toLoad.getCourseId(), toLoad.getPersonId());
                service.createCourseUser(toLoad);
            }
        }
    }

    @RabbitListener(queues = {"{queue.course-user-delete}"})
    public void onCourseUserDeleted(@Payload String payload) {
        System.out.println("EVENTO CANCELLAZIONE: "+payload);
        JSONObject json = new JSONObject(payload);
        CourseUserRelObject toLoad = new CourseUserRelObject();
        toLoad.setCourseId(Long.parseLong(json.get("courseId").toString()));
        toLoad.setPersonId(Long.parseLong(json.get("userId").toString()));
        if(teacherRepo.existsById(toLoad.getPersonId())){
            if(teacherRepo.getCourseIdByTeacher(toLoad.getPersonId()).contains(toLoad.getCourseId())) {
                teacherRepo.deleteAssignedCourse(toLoad.getCourseId(), toLoad.getPersonId());
                service.deleteCourseUser(toLoad);
            }
        }else{
            if(studentRepo.getCourseIdByStudent(toLoad.getPersonId()).contains(toLoad.getCourseId())) {
                studentRepo.deleteLikedCourse(toLoad.getCourseId(), toLoad.getPersonId());
                service.deleteCourseUser(toLoad);
            }
        }
    }
}
