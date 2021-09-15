package com.example.demo.service.impl;

import com.example.demo.entity.ProductEntity;
import com.example.demo.exception.ProductAlreadyExistException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addNewProduct(ProductEntity product) throws ProductAlreadyExistException {
        if (productRepository.existsByTitle(product.getTitle())) {
            throw new ProductAlreadyExistException("Product with this Title already exists");
        }
        productRepository.save(product);
    }

    @Override
    public void updateProduct(String title, ProductEntity productReq) throws ProductAlreadyExistException, ProductNotFoundException {
        ProductEntity productEntity = productRepository.findByTitle(title);
        if (productEntity == null) {
            throw new ProductNotFoundException("Product with this Title not found");
        }
        if (!productReq.getTitle().equals(productEntity.getTitle()) && productRepository.existsByTitle(productReq.getTitle())) {
            throw new ProductAlreadyExistException("Product with this Title already exists");
        }
        productEntity.setTitle(productReq.getTitle());
        productEntity.setDescription(productReq.getDescription());
        productEntity.setPrice(productReq.getPrice());
        if (!productReq.getCategories().isEmpty()) {
            productEntity.setCategories(productReq.getCategories());
        }
        productRepository.save(productEntity);
    }

    @Override
    public List<ProductEntity> getAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity getProductByTitle(String title) throws ProductNotFoundException {
        ProductEntity product = productRepository.findByTitle(title);
        if (product == null) {
            throw new ProductNotFoundException("Product with this Title Number not found");
        }
        return product;
    }

    @Override
    public ProductEntity getProductById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with this ID not found"));
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        productRepository.findById(id).
                orElseThrow(() -> new ProductNotFoundException("Product with this ID not found"));
        productRepository.deleteById(id);
    }

    public void refreshProducts() {

    }
}
