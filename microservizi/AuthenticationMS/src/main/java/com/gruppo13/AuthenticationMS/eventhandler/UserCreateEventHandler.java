package com.gruppo13.AuthenticationMS.eventhandler;

import com.gruppo13.AuthenticationMS.event.UserCreatedEvent;
import com.gruppo13.AuthenticationMS.service.UserServiceSAGA;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Log4j2
@Component
@AllArgsConstructor
public class UserCreateEventHandler {

    private final UserServiceSAGA orderService;

    //@RabbitListener(queues = {"{queue.user-create}"})
    public void onOrderCanceled(@Payload String payload) {

        //UserCreatedEvent event = new Gson().fromJson(payload,UserCreatedEvent.class);

        //System.out.println("EVENTO CREATO DAL MESSAGGIO " + event.getTransactionId() + " , " + event.getUser().getEmail());
        System.out.println("EVENTO: "+payload);

    }
}