package com.gruppo13.ChatMS.configuration;

import com.gruppo13.ChatMS.dto.UserChatEmailDto;
import com.gruppo13.ChatMS.model.User;
import com.gruppo13.ChatMS.repositories.ChatRepository;
import com.gruppo13.ChatMS.repositories.MessageRepository;
import com.gruppo13.ChatMS.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketListener {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    Map<String, UserChatEmailDto> idSession_online = new HashMap<String, UserChatEmailDto>();

    @EventListener
    public void onApplicationEvent(SessionSubscribeEvent event) {
        MessageHeaders headers = event.getMessage().getHeaders();
        String source = headers.get("simpDestination").toString();
        String sessionId = headers.get("simpSessionId").toString();
        String[] parts = source.split("/");
        if (!idSession_online.containsKey(sessionId)) {
            String email = parts[2];
            UserChatEmailDto userChat = new UserChatEmailDto();
            User user = userRepository.findByEmail(email).orElse(null);
            user.setOnline(true);
            userRepository.saveAndFlush(user);
            userChat.setId(user.getId());
            userChat.setEmail(user.getEmail());
            sendStatusMessage(user.getId(), "ONLINE");
            if (userChat != null) {
                idSession_online.put(sessionId, userChat);
            }
        }
    }

    @EventListener
    public void onApplicationEvent(SessionDisconnectEvent event) {
        UserChatEmailDto userChat = idSession_online.get(event.getSessionId());
        User user = userRepository.findById(userChat.getId()).orElse(null);
        user.setOnline(false);
        userRepository.saveAndFlush(user);
        sendStatusMessage(user.getId(), "OFFLINE");
        idSession_online.remove(event.getSessionId());
    }

    public void sendStatusMessage(Long idUser, String status) {
        Map<String, String> messageConverted = new HashMap<String,String>();
        messageConverted.put("from", "SERVER");
        messageConverted.put("status", status);
        messageConverted.put("idUser", idUser.toString());
        idSession_online.forEach((k,v) ->{
            if(v.getId() != idUser) {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/"+ v.getEmail(),messageConverted);
            }
        });
    }
}