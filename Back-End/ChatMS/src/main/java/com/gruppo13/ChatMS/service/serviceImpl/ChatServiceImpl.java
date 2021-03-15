package com.gruppo13.ChatMS.service.serviceImpl;

import com.gruppo13.ChatMS.dto.MessageInfo;
import com.gruppo13.ChatMS.dto.NewChatDto;
import com.gruppo13.ChatMS.dto.UserBasicInfo;
import com.gruppo13.ChatMS.model.Chat;
import com.gruppo13.ChatMS.model.Message;
import com.gruppo13.ChatMS.model.User;
import com.gruppo13.ChatMS.repositories.ChatRepository;
import com.gruppo13.ChatMS.repositories.MessageRepository;
import com.gruppo13.ChatMS.repositories.UserRepository;
import com.gruppo13.ChatMS.response.ChatResponse;
import com.gruppo13.ChatMS.service.ChatService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Set<ChatResponse> getAllChats(User user) {
        Set<ChatResponse> response = new HashSet<ChatResponse>();
        Set<Chat> chats = chatRepository.getUserTagsByUserId(user.getId());
        for(Chat chat: chats){
            ChatResponse chatResponse = new ChatResponse();
            chatResponse.setId(chat.getId());
            chatResponse.setNome(chat.getNome());
            chatResponse.setDataCreazione(chat.getDataCreazione());
            chatResponse.setUltimoMessaggio(getUltimoMessaggio(chat.getMessages()));
            response.add(chatResponse);
        }
        return response;
    }

    private MessageInfo getUltimoMessaggio(List<Message> messages){
        if(messages.isEmpty())
            return null;
        Message lastMessage = Collections.max(messages, Comparator.comparing(c -> c.getDate()));
        MessageInfo messageInfo =new MessageInfo();
        messageInfo.setId(lastMessage.getId());
        messageInfo.setDate(lastMessage.getDate());
        messageInfo.setText(lastMessage.getText());
        UserBasicInfo sender = new UserBasicInfo();
        sender.setEmail(lastMessage.getSender().getEmail());
        sender.setIsProfessor(lastMessage.getSender().isProfessor());
        sender.setName(lastMessage.getSender().getName());
        sender.setSurname(lastMessage.getSender().getSurname());
        sender.setId(lastMessage.getSender().getId());
        messageInfo.setSender(sender);
        return messageInfo;
    }

    @Override
    public Boolean createChat(User user, NewChatDto newChatDto) {
        Chat chat = new Chat();
        if(newChatDto.getIsGroup())
            chat.setNome(newChatDto.getNome());
        for (long id: newChatDto.getIdPartecipanti()){
            User partecipante = userRepository.findById(id).orElse(null);
            if(partecipante != null){
                chat.addPartecipante(partecipante);
            }
        }
        if(chat.getPartecipanti().size()>0){
            User partecipante = userRepository.findById(user.getId()).orElse(null);
            if(partecipante != null){
                chat.addPartecipante(partecipante);
            } else {
                return false;
            }
        } else {
            return false;
        }
        chat.setDataCreazione(new Date());
        chatRepository.saveAndFlush(chat);
        return true;
    }

    @Override
    public Chat getChatById(long idChat) {
        return  chatRepository.findById(idChat).orElse(null);
    }

    @Override
    public void saveMessage(String text, Long idChat, String emailFromUser) {
        Chat chat = chatRepository.findById(idChat).orElse(null);
        if(chat!= null){
            User sender = userRepository.findByEmail(emailFromUser).orElse(null);
            if(sender != null){
                Message message = new Message();
                message.setDate(new Date());
                message.setChat(chat);
                message.setSender(sender);
                message.setText(text);
                messageRepository.saveAndFlush(message);
                chat.getMessages().add(message);
                chatRepository.saveAndFlush(chat);
            }
        }
    }
}
