package com.gruppo13.ChatMS.dto;

import com.gruppo13.ChatMS.model.User;

import java.util.Date;

public class MessageInfo {
    private Long id;
    private String text;
    private Date date;
    private UserBasicInfo sender;
    private Long idChat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserBasicInfo getSender() {
        return sender;
    }

    public void setSender(UserBasicInfo sender) {
        this.sender = sender;
    }

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }
}
