package com.gruppo13.ChatMS.service;

import com.gruppo13.ChatMS.dto.NewChatDto;
import com.gruppo13.ChatMS.model.Chat;
import com.gruppo13.ChatMS.model.User;
import com.gruppo13.ChatMS.response.ChatResponse;

import java.util.Set;

public interface ChatService {
    Set<ChatResponse> getAllChats(User user);

    Boolean createChat(User user, NewChatDto newChatDto);

    Chat getChatById(long idChat);

    void saveMessage(String text, Long idChat, String emailFromUser);

}
