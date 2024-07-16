package com.client_person.exception;

public class CustomException extends RuntimeException {

    private static final long serialVersionID = 1L;

    public CustomException(String message){
        super(message);
    }

}
