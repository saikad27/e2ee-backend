package com.example.e2ee_backend.dto;

import com.example.e2ee_backend.model.UserInfo;

//A list of these are returned after a user logs in
public class UserDTO {
    private String username;
    private String userId;

    public UserDTO(String username, String userId) {
        this.username = username;
        this.userId = userId;
    }
    public UserDTO(UserInfo userInfo){
        this.userId = userInfo.getUserId();
        this.username = userInfo.getUsername();
    }

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
