package com.example.demo.exception;

public class InventoryNotFoundException extends Exception {
    public InventoryNotFoundException(String message) {
        super(message);
    }
}
