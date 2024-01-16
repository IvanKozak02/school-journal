package com.example.demoauth.service;

import com.example.demoauth.models.Message;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.pojo.ID;
import com.example.demoauth.repository.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class MessageService {


    private MessageRepository messageRepository;

    ObjectMapper objectMapper = new ObjectMapper();
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages(UserPersonalData userPersonalData){
        return messageRepository.findByUserPersonalData(userPersonalData);
    }

    public void saveMessage(List<UserPersonalData> userPersonalData, String messageText){

        Message message = new Message();
        message.setMessageText(messageText);
        message.setLocalDateTime(LocalDateTime.now());
        message.setUserPersonalData(userPersonalData);
        message.setStatus("Нове");
        messageRepository.save(message);
    }

    public void save(Message message){
        messageRepository.save(message);
    }

    public void deleteMessage(String jsonData) throws JsonProcessingException {
        ID id = objectMapper.readValue(jsonData, ID.class);
        long messageId = Long.parseLong(id.getId());
        messageRepository.deleteById(messageId);
    }
}
