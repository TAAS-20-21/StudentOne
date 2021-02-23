package com.gruppo13.CalendarMS.eventhandler;

import com.gruppo13.CalendarMS.models.Course;
import com.gruppo13.CalendarMS.repositories.StudentRepository;
import com.gruppo13.CalendarMS.repositories.TeacherRepository;
import com.gruppo13.CalendarMS.repositories.UserRepository;
import com.gruppo13.CalendarMS.util.CourseUserRelObject;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@AllArgsConstructor
public class CourseUserCreatedEventHandler {

    private StudentRepository studentRepo;

    private TeacherRepository teacherRepo;

    @RabbitListener(queues = {"{queue.course-user-create}"})
    public void onCourseUserCreated(@Payload String payload) {
        System.out.println("EVENTO: "+payload);
        JSONObject json = new JSONObject(payload);
        CourseUserRelObject toLoad = new CourseUserRelObject();
        toLoad.setCourseId(Long.parseLong(json.get("courseId").toString()));
        toLoad.setPersonId(Long.parseLong(json.get("userId").toString()));
        if(teacherRepo.existsById(toLoad.getPersonId())){
            teacherRepo.addAssignedCourse(toLoad.getCourseId(),toLoad.getPersonId());
        }else{
            studentRepo.addLikedCourse(toLoad.getCourseId(),toLoad.getPersonId());
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
            teacherRepo.deleteAssignedCourse(toLoad.getCourseId(),toLoad.getPersonId());
        }else{
            studentRepo.deleteLikedCourse(toLoad.getCourseId(),toLoad.getPersonId());
        }
    }
}
