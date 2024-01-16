package com.example.demoauth.controllers;

import com.example.demoauth.models.Application;
import com.example.demoauth.models.Message;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.service.MessageService;
import com.example.demoauth.service.UserDataService;
import com.example.demoauth.service.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class MessageController {

    private UserDataService userDataService;

    private MessageService messageService;

    public MessageController(UserDataService userDataService, MessageService messageService) {
        this.userDataService = userDataService;
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<?> getAllMes(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userDetails.getEmail());
        List<Message> messages =  messageService.getAllMessages(userPersonalData);
        for (Message message : messages) {
            message.setStatus("Переглянуто");
            messageService.save(message);
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
//        }

    }
    @GetMapping("/viewedmessages")
    public @ResponseBody ResponseEntity<?> getAllViewedMes(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserPersonalData userPersonalData = userDataService.getUserByEmail(userDetails.getEmail());
        List<Message> messages =  messageService.getAllMessages(userPersonalData);
        return new ResponseEntity<>(messages, HttpStatus.OK);
//        }

    }

    @PostMapping("/deletemessage")
    public ResponseEntity<?> deleteMessage(@RequestParam(name = "jsonData") String jsonData){
        try {
            messageService.deleteMessage(jsonData);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
