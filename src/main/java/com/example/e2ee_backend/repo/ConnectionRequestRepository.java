package com.example.e2ee_backend.repo;

import com.example.e2ee_backend.model.ConnectionRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest,String> {

    @Query(value="SELECT * FROM connection_request WHERE request_issuer_id =:requestIssuerId AND request_receiver_id = :requestReceiverId",nativeQuery=true)
    public ConnectionRequest findRequest(String requestIssuerId,String requestReceiverId);

    @Query(value="SELECT request_issuer_id FROM connection_request WHERE request_receiver_id = :userId",nativeQuery = true)
    public String findRequestIssuerId(String userId);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM connection_request WHERE request_receiver_id=:requestReceiverId AND request_issuer_id=:requestIssuerId",nativeQuery = true)
    public void deleteConnectionRequest(String requestReceiverId,String requestIssuerId);

    @Query(value="SELECT * FROM connection_request WHERE request_receiver_id=:requestReceiverId",nativeQuery = true)
    public List<ConnectionRequest> findByRequestReceiverId(String requestReceiverId);

    @Query(value="SELECT * FROM connection_request WHERE request_receiver_id=:requestReceiverId AND is_delivered=FALSE",nativeQuery = true)
    public List<ConnectionRequest> findNewConnectionRequests(String requestReceiverId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE connection_request SET is_delivered = TRUE WHERE request_receiver_id = :requestReceiverId",nativeQuery = true)
    public void updateDeliveryStatus(String requestReceiverId);

}
