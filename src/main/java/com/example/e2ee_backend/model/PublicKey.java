package com.example.e2ee_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="public_key")
public class PublicKey {

    @Id
    private String id;
    @Column(unique = true)
    private String userId;
    @Column(name="public_key",columnDefinition="TEXT")
    private String key;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "PublicKeyRepository{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
