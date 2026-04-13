package com.example.e2ee_backend.model;

import com.example.e2ee_backend.dto.MessageDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name="messages")
public class MessageDetail {
    public MessageDetail(){

    }
    public MessageDetail(MessageDTO messageDTO){
        this.messageId = (UUID.randomUUID()).toString();
        this.senderId = messageDTO.getSenderId();
        this.receiverId = messageDTO.getReceiverId();
        this.message = messageDTO.getMessage();
        this.sentAt = Timestamp.valueOf(LocalDateTime.now());
        this.isDelivered = false;
        this.deletedFor = null;
    }
    @Id
    private String messageId;
    private String senderId;
    private String receiverId;
    private String message;
    private Timestamp sentAt;
    private Timestamp receivedAt;
    private boolean isDelivered;
    private String deletedFor;

    public String getDeletedFor() {
        return deletedFor;
    }

    public void setDeletedFor(String deletedFor) {
        this.deletedFor = deletedFor;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    public Timestamp getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Timestamp receivedAt) {
        this.receivedAt = receivedAt;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }


}
