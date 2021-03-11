package com.gruppo13.CoursesMS.event;

import com.gruppo13.CoursesMS.util.CourseUserRelObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CourseUserCreatedEvent {
    private String transactionId;

    private CourseUserRelObject courseUserRel;

}
