package com.gruppo13.CoursesMS.service;

import com.gruppo13.CoursesMS.event.CourseUserCreatedEvent;
import com.gruppo13.CoursesMS.event.CourseUserDeletedEvent;
import com.gruppo13.CoursesMS.util.CourseUserRelObject;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Log4j2
@AllArgsConstructor
@Service
public class CourseUserServiceSAGA {
    private final ApplicationEventPublisher publisher;

    @Transactional
    public CourseUserRelObject createCourseUser(CourseUserRelObject courseUser) {

        System.out.println("Saving a course-user {} " + courseUser.getPersonId());

        publishCourseUserCreate(courseUser);

        return courseUser;

    }

    @Transactional
    public CourseUserRelObject deleteCourseUser(CourseUserRelObject courseUser) {

        System.out.println("Deleting a course-user {} " + courseUser.getPersonId());

        publishCourseUserDelete(courseUser);

        return courseUser;

    }

    private void publishCourseUserCreate(CourseUserRelObject courseUser) {

        CourseUserCreatedEvent event = new CourseUserCreatedEvent(UUID.randomUUID().toString(), courseUser);

        System.out.println("Publishing an course-user created event {} " + event.getTransactionId() + ", " + event.getCourseUserRel().getPersonId());

        publisher.publishEvent(event);

    }

    private void publishCourseUserDelete(CourseUserRelObject courseUser) {

        CourseUserDeletedEvent event = new CourseUserDeletedEvent(UUID.randomUUID().toString(), courseUser);

        System.out.println("Publishing an course-user delete event {} " + event.getTransactionId() + ", " + event.getCourseUserRel().getPersonId());

        publisher.publishEvent(event);

    }
}
