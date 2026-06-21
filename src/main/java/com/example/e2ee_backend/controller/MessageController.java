package com.example.e2ee_backend.controller;

import com.example.e2ee_backend.dto.DeleteMessagesDTO;
import com.example.e2ee_backend.dto.MessageDTO;
import com.example.e2ee_backend.service.MessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class MessageController {

    private final MessageService messageService;
    private final Set<String> newMessageUserIds;

    public MessageController(MessageService messageService,@Qualifier("newMessageUserIds") Set<String> newMessageUserIds){
        this.messageService = messageService;
        this.newMessageUserIds = newMessageUserIds;
    }

    @PostMapping("/send")
    public void send(@RequestBody MessageDTO messageDTO){
        System.out.println(messageDTO);
        newMessageUserIds.add(messageDTO.getReceiverId());
        messageService.saveMessage(messageDTO);
    }


    @PostMapping("/fetch/new/messages")
    public ResponseEntity<List<MessageDTO>> fetchNewMessages(@AuthenticationPrincipal Jwt jwt){
        System.out.println("Fetching new messages for user : "+jwt.getClaimAsString("name"));

        if(newMessageUserIds.remove(jwt.getClaimAsString("sub"))) {
            return ResponseEntity.ok(messageService.fetchNewMessages(jwt.getClaimAsString("sub")));
        }else{
            return ResponseEntity.noContent().build();
        }

    }

    @GetMapping("/fetch/all/messages")
    public List<MessageDTO> fetchMessages(@AuthenticationPrincipal Jwt jwt,@RequestParam String userId){
        System.out.println("/fetch/messages controller called");
        System.out.println("Sender id : "+jwt.getClaimAsString("sub")+", receiverId : "+userId);
        List<MessageDTO> messageDTOS = messageService.fetchMessages(jwt.getClaimAsString("sub"),userId);
        for(MessageDTO messageDTO : messageDTOS){
            System.out.println(messageDTO);
        }
        return messageDTOS;
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
