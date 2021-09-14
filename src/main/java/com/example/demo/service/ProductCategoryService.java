package com.example.demo.service;

import com.example.demo.entity.ProductCategoryEntity;
import com.example.demo.exception.ProductCategoryAlreadyExistException;
import com.example.demo.exception.ProductCategoryNotFoundException;

import java.util.List;

public interface ProductCategoryService {
    void addNewCategory(String title) throws ProductCategoryAlreadyExistException;

    List<ProductCategoryEntity> getAll();

    void deleteCategory(Long id) throws ProductCategoryNotFoundException;
}
