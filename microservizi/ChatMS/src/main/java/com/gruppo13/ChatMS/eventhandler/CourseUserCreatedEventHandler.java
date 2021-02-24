package com.gruppo13.ChatMS.eventhandler;

import com.gruppo13.ChatMS.model.Chat;
import com.gruppo13.ChatMS.model.User;
import com.gruppo13.ChatMS.repositories.ChatRepository;
import com.gruppo13.ChatMS.repositories.UserRepository;
import com.gruppo13.ChatMS.service.CourseUserServiceSAGA;
import com.gruppo13.ChatMS.util.CourseUserRelObject;
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
    private UserRepository userRepo;

    @Autowired
    private ChatRepository chatRepo;

    @Autowired
    private CourseUserServiceSAGA service;

    @RabbitListener(queues = {"{queue.course-user-create}"})
    public void onCourseUserCreated(@Payload String payload) {
        System.out.println("EVENTO: "+payload);
        JSONObject json = new JSONObject(payload);
        CourseUserRelObject toLoad = new CourseUserRelObject();
        toLoad.setCourseId(Long.parseLong(json.get("courseId").toString()));
        toLoad.setPersonId(Long.parseLong(json.get("userId").toString()));
        Chat chat = chatRepo.findByCourseId(toLoad.getCourseId()).orElse(null);
        if(userRepo.existsById(toLoad.getPersonId()) && chat != null){
            User user = userRepo.findById(toLoad.getPersonId()).get();
            if(!chat.getPartecipanti().contains(user)) {
                chatRepo.addChatUser(chat.getId(),user.getId());
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
        Chat chat = chatRepo.findByCourseId(toLoad.getCourseId()).orElse(null);
        if(userRepo.existsById(toLoad.getPersonId()) && chat != null){
            User user = userRepo.findById(toLoad.getPersonId()).get();
            if(!chat.getPartecipanti().contains(user)) {
                chatRepo.deleteChatUser(chat.getId(),user.getId());
                service.deleteCourseUser(toLoad);
            }
        }
    }
}
