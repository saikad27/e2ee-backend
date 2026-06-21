package com.example.e2ee_backend.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.stream.Collectors;

@Service
public class OtpService {
    private final SecureRandom secureRandom = new SecureRandom();
    private String otp;
    private final EmailService emailService;
    public OtpService(EmailService emailService){
        this.emailService = emailService;
    }
    public String generate(){
        return secureRandom.ints(6,0,10).mapToObj( a -> String.valueOf(a) ).collect(Collectors.joining());
    }

    @Async
    public void send(String toEmail){
        this.otp = generate();
        emailService.sendOtp(toEmail,otp);
        try {
            Thread.sleep(60000);
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }
        this.otp = null;
    }



    public String verify(String otp){
        if(this.otp==null || !this.otp.equals(otp)){
            return "invalid";
        }else{
            return "valid";
        }
    }
}
