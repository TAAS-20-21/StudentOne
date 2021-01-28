package com.gruppo13.AuthenticationMS.dto;

import java.util.List;

import lombok.Value;

@Value
public class UserInfo {
    private String id, name, surname, email;
    private List<String> roles;
}