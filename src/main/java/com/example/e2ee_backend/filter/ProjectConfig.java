package com.example.e2ee_backend.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

    @Value("${keySetURI}")
    private String keySetURI;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.csrf( c -> c.disable());
        httpSecurity.oauth2ResourceServer(c -> c.jwt(
                j -> j.jwkSetUri(keySetURI)
        ));
        return httpSecurity.build();
    }

}
