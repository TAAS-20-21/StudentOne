package com.gruppo13.CoursesMS.eventlistener;

import com.gruppo13.CoursesMS.CoursesMsApplication;
import com.gruppo13.CoursesMS.event.CourseCreatedEvent;
import com.gruppo13.CoursesMS.event.CourseUserCreatedEvent;
import com.gruppo13.CoursesMS.event.CourseUserDeletedEvent;
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
public class CourseUserEventListener {
    private final RabbitTemplate rabbitTemplate;
    private final String queueCourseUserCreateName;
    private final String queueCourseUserDeleteName;

    public CourseUserEventListener(RabbitTemplate rabbitTemplate, @Value("{queue.course-user-create}") String queueCourseUserCreateName,
                                   @Value("{queue.course-user-delete}") String queueCourseUserDeleteName) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueCourseUserCreateName = queueCourseUserCreateName;
        this.queueCourseUserDeleteName = queueCourseUserDeleteName;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreateEvent(CourseUserCreatedEvent event) {

        System.out.println("Sending order created event to {}, event: {}" + queueCourseUserCreateName + " , " + event);
        JSONObject jo = new JSONObject();
        jo.put("transactionId",event.getTransactionId().toString());
        jo.put("courseId",event.getCourseUserRel().getCourseId());
        jo.put("userId",event.getCourseUserRel().getPersonId());
        //rabbitTemplate.convertAndSend(queueOrderCreateName,jo.toString());
        rabbitTemplate.convertAndSend(CoursesMsApplication.getTopicExchangeCourseUserCreate(),"course-user.create.course",jo.toString());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onDeleteEvent(CourseUserDeletedEvent event) {

        System.out.println("Sending order deleted event to {}, event: {}" + queueCourseUserDeleteName + " , " + event);
        JSONObject jo = new JSONObject();
        jo.put("transactionId",event.getTransactionId().toString());
        jo.put("courseId",event.getCourseUserRel().getCourseId());
        jo.put("userId",event.getCourseUserRel().getPersonId());
        //rabbitTemplate.convertAndSend(queueOrderCreateName,jo.toString());
        System.out.println("PRIMA DI CONVERT AND SEND");
        rabbitTemplate.convertAndSend(CoursesMsApplication.getTopicExchangeCourseUserDelete(),"course-user.delete.course",jo.toString());
        System.out.println("DOPO DI CONVERT AND SEND");
    }
}
