package com.example.demo.exception;

public class ProductCategoryAlreadyExistException extends Exception {
    public ProductCategoryAlreadyExistException(String message) {
        super(message);
    }
}
