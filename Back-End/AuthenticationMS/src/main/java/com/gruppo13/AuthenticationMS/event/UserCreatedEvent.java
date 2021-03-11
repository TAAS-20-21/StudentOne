package com.gruppo13.AuthenticationMS.event;

import com.gruppo13.AuthenticationMS.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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