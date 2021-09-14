package com.example.demo.exception;

public class CustomerAlreadyExistException extends Exception {
    public CustomerAlreadyExistException(String message) {
        super(message);
    }
}
