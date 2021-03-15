package com.gruppo13.AuthenticationMS.event;

import com.gruppo13.AuthenticationMS.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserCanceledEvent {

    private String transactionId;

    private User user;
}