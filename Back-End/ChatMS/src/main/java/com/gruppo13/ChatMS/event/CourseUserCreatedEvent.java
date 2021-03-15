package com.gruppo13.ChatMS.event;

import com.gruppo13.ChatMS.util.CourseUserRelObject;
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
