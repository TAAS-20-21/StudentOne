package com.gruppo13.CalendarMS.event;

import com.gruppo13.CalendarMS.models.User;
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