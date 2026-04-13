package com.example.e2ee_backend.dto;

import java.sql.Timestamp;

public class MessageDTO {
    private String senderId;
    private String receiverId;
    private String messageId;
    private String message;
    private Timestamp sentAt;
    private Timestamp receivedAt;
    public MessageDTO(){

    }
    public MessageDTO(String messageId,String senderId,String receiverId,String message,Timestamp sentAt,Timestamp receivedAt){
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.sentAt = sentAt;
        this.receivedAt = receivedAt;
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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
