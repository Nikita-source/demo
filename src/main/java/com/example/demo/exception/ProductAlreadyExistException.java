package com.example.demo.exception;

public class ProductAlreadyExistException extends Exception {
    public ProductAlreadyExistException(String message) {
        super(message);
    }
}
