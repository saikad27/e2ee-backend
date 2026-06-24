package com.example.e2ee_backend.repo;


import com.example.e2ee_backend.model.PublicKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicKeyRepository extends JpaRepository<PublicKey,String> {

    @Query(value="SELECT public_key FROM public_key WHERE user_id=:userId",nativeQuery = true)
    public String getPublicKey(String userId);
}
