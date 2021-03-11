package com.gruppo13.CoursesMS.event;

import com.gruppo13.CoursesMS.model.User;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserCreatedEvent implements Serializable {

    private String transactionId;

    private User user;

}