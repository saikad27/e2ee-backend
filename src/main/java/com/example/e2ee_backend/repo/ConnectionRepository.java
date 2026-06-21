package com.example.e2ee_backend.repo;

import com.example.e2ee_backend.model.Connection;
import com.example.e2ee_backend.model.UserInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection,String> {

    @Query(value = "SELECT * FROM connection WHERE (user_id1 = :userId1 AND user_id2 = :userId2) OR (user_id1 = :userId2 AND user_id2 = :userId1)",nativeQuery = true)
    public Connection findConnection(String userId1,String userId2);

    @Query(value = "(SELECT user_id1 FROM connection WHERE user_id2=:userId) UNION (SELECT user_id2 FROM connection WHERE user_id1=:userId)",nativeQuery = true)
    public List<String> getAssociatedUsers(String userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE connection SET status = 'Active' WHERE user_a_id=:requestIssuerId AND user_b_id = :requestReceiverId",nativeQuery = true)
    public void updateConnectionStatus(String requestIssuerId,String requestReceiverId);
}
