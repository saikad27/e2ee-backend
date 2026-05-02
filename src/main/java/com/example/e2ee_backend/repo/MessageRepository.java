package com.example.e2ee_backend.repo;

import com.example.e2ee_backend.model.MessageDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageDetail,String> {

    @Query(value = "SELECT * FROM messages WHERE receiver_id = :userId AND is_delivered = 0",nativeQuery = true)
    public List<MessageDetail> getNewMessages(String userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE messages SET received_at = CURRENT_TIMESTAMP ,is_delivered = 1 WHERE receiver_id = :userId AND is_delivered = 0",nativeQuery = true)
    public void updateUndeliveredMessageStatus(String userId);

    //This method returns all the messages
    @Query(value="SELECT * FROM messages WHERE (sender_id = :userId AND receiver_id = :chatUserId) OR (receiver_id = :userId AND sender_id = :chatUserId) ",nativeQuery=true)
    public List<MessageDetail> getMessages(String userId,String chatUserId);
}
