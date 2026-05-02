package com.example.e2ee_backend.service;

import com.example.e2ee_backend.dto.MessageDTO;
import com.example.e2ee_backend.model.MessageDetail;
import com.example.e2ee_backend.repo.MessageRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }
    public void saveMessage(MessageDTO messageDTO){
        messageRepository.save(new MessageDetail(messageDTO));
    }
    public List<MessageDTO> fetchNewMessages(String userId){
        List<MessageDetail> newMessages = messageRepository.getNewMessages(userId);
        messageRepository.updateUndeliveredMessageStatus(userId);
        return newMessages.stream().map(m ->
                new MessageDTO(m.getMessageId(),m.getSenderId(),m.getReceiverId(),m.getMessage(),m.getSentAt(), Timestamp.valueOf(LocalDateTime.now()))).toList();
    }
    public List<MessageDTO> fetchMessages(String userId,String chatUserId){
        List<MessageDetail> messages = messageRepository.getMessages(userId, chatUserId);
        return messages.stream().map(m ->
                new MessageDTO(m.getMessageId(),m.getSenderId(),m.getReceiverId(),m.getMessage(),m.getSentAt(),m.getReceivedAt())).toList();
    }

    public void deleteMessage(String messageId){
        messageRepository.deleteById(messageId);
    }

    public void deleteMessages(List<String> messageIdList){
        messageRepository.deleteAllById(messageIdList);
    }
}
