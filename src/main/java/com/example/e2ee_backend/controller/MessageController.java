package com.example.e2ee_backend.controller;

import com.example.e2ee_backend.dto.DeleteMessagesDTO;
import com.example.e2ee_backend.dto.MessageDTO;
import com.example.e2ee_backend.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    private final MessageService messageService;
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public void send(@RequestBody MessageDTO messageDTO){
        messageService.saveMessage(messageDTO);
    }

    //This method will be called after user logs in
    @PostMapping("/fetch/new/messages")
    public List<MessageDTO> fetchNewMessages(@RequestParam String userId){
        return messageService.fetchNewMessages(userId);
    }

    @GetMapping("/fetch/messages")
    public List<MessageDTO> fetchMessages(@RequestParam String userId,@RequestParam String chatUserId){
        System.out.println("/fetch/messages controller called");
        return messageService.fetchMessages(userId,chatUserId);
    }


    @DeleteMapping("/delete")
    public void deleteMessage(@RequestParam String messageId){
        messageService.deleteMessage(messageId);
    }

    @DeleteMapping("/delete/messages")
    public void deleteMessages(@RequestBody DeleteMessagesDTO deleteMessagesDTO){
        messageService.deleteMessages(deleteMessagesDTO.getMessageIdList());
    }

    @PatchMapping("/edit")
    public void editMessage(@RequestParam String messageId){

    }

}
