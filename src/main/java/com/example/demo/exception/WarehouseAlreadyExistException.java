package com.example.demo.exception;

public class WarehouseAlreadyExistException extends Exception {
    public WarehouseAlreadyExistException(String message) {
        super(message);
    }
}
