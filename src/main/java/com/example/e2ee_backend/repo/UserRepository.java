package com.example.e2ee_backend.repo;

import com.example.e2ee_backend.dto.UserDTO;
import com.example.e2ee_backend.model.UserInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository extends JpaRepository<UserInfo,String> {



    @Query(value = "SELECT * FROM user_info WHERE username = :username",nativeQuery = true)
    public UserInfo findUserByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user_info SET hashed_aadhaar = :hashedAadhaar, aadhaar_verified = TRUE WHERE user_id = :userId AND aadhaar_verified = FALSE",nativeQuery = true)
    public void saveHashedAadhaar(String userId,byte[] hashedAadhaar) throws SQLException;

}
