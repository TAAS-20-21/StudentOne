package com.gruppo13.CalendarMS.eventlistener;

import com.gruppo13.CalendarMS.event.UserCreatedEvent;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Log4j2
@Component
public class UserEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final String queueOrderCreateName;

    public UserEventListener(RabbitTemplate rabbitTemplate,
                             @Value("{queue.user-create}") String queueOrderCreateName) {

        this.rabbitTemplate = rabbitTemplate;
        this.queueOrderCreateName = queueOrderCreateName;

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreateEvent(UserCreatedEvent event) {

        System.out.println("Sending order created event to {}, event: {}" + queueOrderCreateName + " , " + event);
        JSONObject jo = new JSONObject();
        jo.put("transactionId",event.getTransactionId().toString());

        jo.put("id",event.getUser().getId().toString());
        jo.put("email",event.getUser().getEmail());
        jo.put("name",event.getUser().getName());
        jo.put("surname",event.getUser().getSurname());
        jo.put("isProfessor",event.getUser().isProfessor());
        jo.put("provider",event.getUser().getProvider());
        jo.put("providerUserId",event.getUser().getProviderUserId());
        jo.put("password",event.getUser().getPassword());


        /*Set<Role> roles = event.getUser().getRoles();
        JSONArray rolesArray = new JSONArray();
        for(Role r : roles){
            rolesArray.put(r.getTypeRole());
        }
        jo.put("roles",rolesArray);*/



        System.out.println(jo.toString());
        //rabbitTemplate.convertAndSend(queueOrderCreateName,jo.toString());
        rabbitTemplate.convertAndSend("{exchange.user-create}","user.create.calendar",jo.toString());
    }

}