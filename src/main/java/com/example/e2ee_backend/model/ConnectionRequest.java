package com.example.e2ee_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="connection_request")
public class ConnectionRequest {

    @Id
    private String requestId;

    private String requestIssuerId;
    private String requestReceiverId;
    private LocalDateTime createdAt;
    private boolean isDelivered;

    public ConnectionRequest() {
    }

    public ConnectionRequest(String requestIssuerId, String requestReceiverId) {
        this.requestId = UUID.randomUUID().toString();
        this.requestIssuerId = requestIssuerId;
        this.requestReceiverId = requestReceiverId;
        this.createdAt = LocalDateTime.now();
        this.isDelivered = false;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestIssuerId() {
        return requestIssuerId;
    }

    public void setRequestIssuerId(String requestIssuerId) {
        this.requestIssuerId = requestIssuerId;
    }

    public String getRequestReceiverId() {
        return requestReceiverId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public void setRequestReceiverId(String requestReceiverId) {
        this.requestReceiverId = requestReceiverId;
    }

}
