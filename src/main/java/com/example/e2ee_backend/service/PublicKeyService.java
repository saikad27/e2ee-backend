package com.example.e2ee_backend.service;

import com.example.e2ee_backend.dto.PublicKeyDTO;
import com.example.e2ee_backend.model.PublicKey;
import com.example.e2ee_backend.repo.PublicKeyRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PublicKeyService {
    private final PublicKeyRepository publicKeyRepository;
    public PublicKeyService(PublicKeyRepository publicKeyRepository){
        this.publicKeyRepository = publicKeyRepository;
    }

    public PublicKeyDTO getPublicKey(String userId){
        return new PublicKeyDTO(publicKeyRepository.getPublicKey(userId));
    }

    public void savePublicKey(String userId,String key){
        PublicKey publicKey = new PublicKey();
        publicKey.setId(UUID.randomUUID().toString());
        publicKey.setUserId(userId);
        publicKey.setKey(key);
        publicKeyRepository.save(publicKey);
    }
}
