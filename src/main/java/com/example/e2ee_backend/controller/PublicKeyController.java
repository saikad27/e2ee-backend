package com.example.e2ee_backend.controller;


import com.example.e2ee_backend.dto.PublicKeyDTO;
import com.example.e2ee_backend.service.PublicKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PublicKeyController {
    private final PublicKeyService publicKeyService;

    public PublicKeyController(PublicKeyService publicKeyService) {
        this.publicKeyService = publicKeyService;
    }

    @GetMapping("/keys/{userId}")
    public ResponseEntity<PublicKeyDTO> getKey(@PathVariable String userId){
        return ResponseEntity.ok().body(publicKeyService.getPublicKey(userId));
    }

    @PostMapping("/keys/upload")
    public ResponseEntity<?> uploadKey(@AuthenticationPrincipal Jwt jwt, @RequestBody PublicKeyDTO publicKeyDTO){
        publicKeyService.savePublicKey(jwt.getClaimAsString("sub"),publicKeyDTO.getPublicKey());
        return ResponseEntity.ok().body("");
    }
}
