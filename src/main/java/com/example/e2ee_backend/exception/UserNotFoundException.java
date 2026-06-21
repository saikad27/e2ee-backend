package com.example.e2ee_backend.exception;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(){
        super("User not found!");
    }
}
