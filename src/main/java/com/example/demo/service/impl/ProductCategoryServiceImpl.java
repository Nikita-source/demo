package com.example.demo.service.impl;

import com.example.demo.entity.ProductCategoryEntity;
import com.example.demo.exception.ProductCategoryAlreadyExistException;
import com.example.demo.exception.ProductCategoryNotFoundException;
import com.example.demo.repository.ProductCategoryRepository;
import com.example.demo.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public void addNewCategory(String title) throws ProductCategoryAlreadyExistException {
        if (productCategoryRepository.existsByTitle(title)) {
            throw new ProductCategoryAlreadyExistException("Product category with this Title already exists");
        }
        ProductCategoryEntity category = new ProductCategoryEntity();
        category.setTitle(title);
        productCategoryRepository.save(category);
    }

    @Override
    public List<ProductCategoryEntity> getAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public void deleteCategory(Long id) throws ProductCategoryNotFoundException {
        productCategoryRepository.findById(id).
                orElseThrow(() -> new ProductCategoryNotFoundException("Product category with this ID not found"));
        productCategoryRepository.deleteById(id);
    }
}
