package com.gruppo13.CoursesMS.event;


import com.gruppo13.CoursesMS.model.Course;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CourseCreatedEvent {
    private String transactionId;

    private Course course;
}
