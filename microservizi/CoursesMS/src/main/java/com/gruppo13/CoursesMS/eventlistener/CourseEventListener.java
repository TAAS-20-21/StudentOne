package com.gruppo13.CoursesMS.eventlistener;

import com.gruppo13.CoursesMS.CoursesMsApplication;
import com.gruppo13.CoursesMS.event.CourseCreatedEvent;
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
public class CourseEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final String queueOrderCreateName;

    public CourseEventListener(RabbitTemplate rabbitTemplate,
                               @Value("{queue.course-create}") String queueOrderCreateName) {

        this.rabbitTemplate = rabbitTemplate;
        this.queueOrderCreateName = queueOrderCreateName;

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreateEvent(CourseCreatedEvent event) {

        System.out.println("Sending order created event to {}, event: {}" + queueOrderCreateName + " , " + event);
        JSONObject jo = new JSONObject();
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + jo.toString());
        jo.put("transactionId",event.getTransactionId().toString());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + event.getCourse().getId().toString());
        jo.put("id",event.getCourse().getId().toString());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + jo.toString());
        jo.put("cfu",event.getCourse().getCFU());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + jo.toString());
        jo.put("name",event.getCourse().getName());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + jo.toString());
        jo.put("info",event.getCourse().getInfo());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + jo.toString());
        jo.put("lessonHours",event.getCourse().getLesson_hours());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + jo.toString());
        //rabbitTemplate.convertAndSend(queueOrderCreateName,jo.toString());
        rabbitTemplate.convertAndSend(CoursesMsApplication.getTopicExchangeName(),"course.create.try",jo.toString());
    }

}