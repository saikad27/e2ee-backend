package com.example.e2ee_backend.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="connection",uniqueConstraints = {
        @UniqueConstraint(name="uk_connection",columnNames={"user_id1","user_id2"})
})
public class Connection {
    @Id
    private String id;
    @Column(name="user_id1",nullable = false)
    private String userId1;
    @Column(name="user_id2",nullable = false)
    private String userId2;

    public Connection() {
    }

    public Connection(String userId1, String userId2){
        this.id = UUID.randomUUID().toString();
        this.userId1 = userId1;
        this.userId2 = userId2;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_a_id() {
        return userId1;
    }

    public void setUser_a_id(String userId1) {
        this.userId1 = userId1;
    }

    public String getUser_b_id() {
        return userId2;
    }

    public void setUser_b_id(String user_b_id) {
        this.userId2 = userId2;
    }

}
