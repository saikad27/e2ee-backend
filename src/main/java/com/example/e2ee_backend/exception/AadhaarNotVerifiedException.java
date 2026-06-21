package com.example.e2ee_backend.exception;

public class AadhaarNotVerifiedException extends Exception{
    public AadhaarNotVerifiedException(){
        super("User Aadhaar is not verified!");
    }
}
