package com.gruppo13.ChatMS.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppo13.ChatMS.configuration.CurrentUser;
import com.gruppo13.ChatMS.dto.LocalUser;
import com.gruppo13.ChatMS.dto.MessageInfo;
import com.gruppo13.ChatMS.dto.UserBasicInfo;
import com.gruppo13.ChatMS.model.Chat;
import com.gruppo13.ChatMS.model.User;
import com.gruppo13.ChatMS.repositories.ChatRepository;
import com.gruppo13.ChatMS.repositories.UserRepository;
import com.gruppo13.ChatMS.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/socket")
public class MessagingController {

    @Autowired
    private ChatRepository chatRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatService chatService;

    @Transactional
    @SuppressWarnings("unchecked")
    @MessageMapping("/send/message")
    public Map<String, String> SocketChat(String message) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> messageConverted = null;
        try {
            messageConverted = mapper.readValue(message, Map.class);
        } catch (IOException e) {
            messageConverted = null;
        }
        if (messageConverted != null) {
            if (messageConverted.containsKey("idChat") && messageConverted.get("idChat") != null) {
                Chat chat = chatRepo.findById(Long.parseLong(messageConverted.get("idChat"))).orElse(null);
                if (chat != null) {
                    Set<User> partecipanti = chat.getPartecipanti();
                    for (User partecipante : partecipanti) {
                        if (partecipante != null && !(partecipante.getEmail().equalsIgnoreCase(messageConverted.get("emailFromUser")))) {
                            this.simpMessagingTemplate.convertAndSend(
                                    "/socket-publisher/" + partecipante.getEmail(), costructMessage(messageConverted));

                        }
                    }
                }
            }
            chatService.saveMessage(messageConverted.get("text"), Long.parseLong(messageConverted.get("idChat")),
                    messageConverted.get("emailFromUser"));
        }

        return messageConverted;
    }

    private MessageInfo costructMessage(Map<String, String> messageConverted){
        MessageInfo message = new MessageInfo();
        message.setIdChat(Long.parseLong(messageConverted.get("idChat")));
        User user = userRepository.findByEmail(messageConverted.get("emailFromUser")).orElse(null);
        UserBasicInfo sender = new UserBasicInfo();
        if(user != null){
            sender.setId(user.getId());
            sender.setSurname(user.getSurname());
            sender.setName(user.getName());
            sender.setEmail(user.getEmail());
            sender.setIsProfessor(user.isProfessor());
        }
        message.setSender(sender);
        message.setText(messageConverted.get("text"));
        message.setDate(new Date());
        return message;
    }
}
