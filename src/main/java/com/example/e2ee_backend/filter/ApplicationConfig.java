package com.example.e2ee_backend.filter;


import com.example.e2ee_backend.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@EnableScheduling
@Configuration
public class ApplicationConfig {

//    @Bean
//    public ConcurrentHashMap<String,DeferredResult<List<UserDTO>>> connectionRequestHashMap(){
//        return new ConcurrentHashMap<>();
//    }
//
//    @Bean
//    public ConcurrentHashMap<String,LocalDateTime> lastPolledIncomingRequests(){
//        return new ConcurrentHashMap<>();
//    }
    @Bean
    public Set<String> newMessageUserIds(){
        return Collections.synchronizedSet(new HashSet<>());
    }

    @Bean
    public Set<String> newRequestUserIds(){
        return Collections.synchronizedSet(new HashSet<>());
    }




}