package com.example.demo.exception;

public class InventoryAlreadyExistException extends Exception {
    public InventoryAlreadyExistException(String message) {
        super(message);
    }
}
