package com.gruppo13.CalendarMS.event;

import com.gruppo13.CalendarMS.util.CourseUserRelObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CourseUserDeletedEvent {
    private String transactionId;

    private CourseUserRelObject courseUserRel;

}
