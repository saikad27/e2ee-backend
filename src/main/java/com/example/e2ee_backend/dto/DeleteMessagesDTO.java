package com.example.e2ee_backend.dto;

import java.util.List;

public class DeleteMessagesDTO {
    List<String> messageIdList;

    public List<String> getMessageIdList() {
        return messageIdList;
    }

    public void setMessageIdList(List<String> messageIdList) {
        this.messageIdList = messageIdList;
    }

    @Override
    public String toString() {
        return "DeleteMessagesDTO{" +
                "messageIdList=" + messageIdList +
                '}';
    }
}
