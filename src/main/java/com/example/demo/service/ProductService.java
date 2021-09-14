package com.example.demo.service;

import com.example.demo.entity.ProductEntity;
import com.example.demo.exception.ProductAlreadyExistException;
import com.example.demo.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    void addNewProduct(ProductEntity product) throws ProductAlreadyExistException;

    void updateProduct(String title, ProductEntity product) throws ProductAlreadyExistException, ProductNotFoundException;

    List<ProductEntity> getAll();

    ProductEntity getProductByTitle(String title) throws ProductNotFoundException;

    ProductEntity getProductById(Long id) throws ProductNotFoundException;

    void deleteProduct(Long id) throws ProductNotFoundException;
}
