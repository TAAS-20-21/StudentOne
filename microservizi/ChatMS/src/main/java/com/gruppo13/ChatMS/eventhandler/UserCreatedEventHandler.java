package com.gruppo13.ChatMS.eventhandler;



import com.gruppo13.ChatMS.model.User;

import com.gruppo13.ChatMS.repositories.UserRepository;
import com.gruppo13.ChatMS.service.UserServiceSAGA;
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
public class UserCreatedEventHandler {

    private UserRepository userRepo;

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
            service.createUser(toLoad);
        }
    }
}