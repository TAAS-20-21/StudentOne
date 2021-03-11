package com.gruppo13.ChatMS.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppo13.ChatMS.configuration.CurrentUser;
import com.gruppo13.ChatMS.dto.LocalUser;
import com.gruppo13.ChatMS.dto.NewChatDto;
import com.gruppo13.ChatMS.service.ChatService;
import com.gruppo13.ChatMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;


    @GetMapping("/getAllChats")
    public ResponseEntity<?> getAllChats(@CurrentUser LocalUser user) {
        return ResponseEntity.ok(chatService.getAllChats(user.getUser()));
    }

    @PostMapping("/newChat")
    public ResponseEntity<?> createChat(@CurrentUser LocalUser user, @RequestBody NewChatDto newChatDto){
        return ResponseEntity.ok(chatService.createChat(user.getUser(), newChatDto));
    }

    @PostMapping("/getChat")
    public ResponseEntity<?> getChat(@RequestBody Long idChat){
        return ResponseEntity.ok(chatService.getChatById(idChat));
    }

}
