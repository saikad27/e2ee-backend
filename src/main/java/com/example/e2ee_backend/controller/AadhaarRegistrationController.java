package com.example.e2ee_backend.controller;


import com.example.e2ee_backend.dto.OtpDTO;
import com.example.e2ee_backend.repo.UserRepository;
import com.example.e2ee_backend.service.OtpService;
import com.example.e2ee_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;

@RestController
public class AadhaarRegistrationController {

    private final UserService userService;
    private final OtpService otpService;

    public AadhaarRegistrationController(UserService userService,OtpService otpService){
        this.userService = userService;
        this.otpService = otpService;
    }

    //3
    @PostMapping("/register/aadhaar")
    public ResponseEntity<String> registerAadhaar(@AuthenticationPrincipal Jwt jwt, @RequestBody String aadhaar)throws NoSuchAlgorithmException {
        System.out.println("Registering Aadhaar");
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashedAadhaar = messageDigest.digest(aadhaar.getBytes(StandardCharsets.UTF_8));
        try {
            userService.saveHashedAadhaar(jwt.getClaimAsString("sub"), hashedAadhaar);
        }catch(SQLException sqlE){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This aadhaar is already registered with another account");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Aadhaar Registered Successfully");
    }


    @PostMapping("/get/otp")
    public void getOtp(@AuthenticationPrincipal Jwt jwt){
        otpService.send(jwt.getClaimAsString("email"));
    }


    @PostMapping("/verify/otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpDTO otpDto){
        String result = otpService.verify(otpDto.getOtp());
        if(result.equals("valid")){
            return ResponseEntity.ok().body(result);
        }else{
            return ResponseEntity.badRequest().body(result);
        }
    }

}
