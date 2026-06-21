package com.example.e2ee_backend.dto;

public class ConnectionDTO {
    private String requestIssuerId;
    private String userId;  //
    private String requestIssuerName;
    private String userName;    //User being searched
    private String connectionStatus;

    public ConnectionDTO(String userId,String userName,String connectionStatus){
        this.userId = userId;
        this.userName = userName;
        this.connectionStatus = connectionStatus;
    }


    public String getRequestIssuerId() {
        return requestIssuerId;
    }

    public void setRequestIssuerId(String requestIssuerId) {
        this.requestIssuerId = requestIssuerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequestIssuerName() {
        return requestIssuerName;
    }

    public void setRequestIssuerName(String requestIssuerName) {
        this.requestIssuerName = requestIssuerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}
