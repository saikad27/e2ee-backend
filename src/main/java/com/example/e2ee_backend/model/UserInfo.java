package com.example.e2ee_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Arrays;
import java.util.UUID;

@Entity(name="user_info")
public class UserInfo {

    public UserInfo(String userId, String username,String email) {
        this.userId = UUID.randomUUID().toString();
        this.username = username;
        //this.eAadhaar = eAadhaar;
        this.email = email;
        this.isAadhaarVerified = false;
    }

    public UserInfo() {
    }

    @Id
    private String userId;

    @Column(unique = true)
    private String username;

    @Column(unique= true,name ="hashed_aadhaar")
    private byte[] hashedAadhaar;

    @Column(unique = true)
    private String email;

    @Column(name="aadhaar_verified")
    private boolean isAadhaarVerified;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getHashedAadhaar() {
        return hashedAadhaar;
    }

    public void setHashedAadhaar(byte[] hashedAadhaar) {
        this.hashedAadhaar = hashedAadhaar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAadhaarVerified() {
        return isAadhaarVerified;
    }

    public void setAadhaarVerified(boolean aadhaarVerified) {
        isAadhaarVerified = aadhaarVerified;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", hashedAadhaar=" + Arrays.toString(hashedAadhaar) +
                ", email='" + email + '\'' +
                ", isAadhaarVerified=" + isAadhaarVerified +
                '}';
    }
}
