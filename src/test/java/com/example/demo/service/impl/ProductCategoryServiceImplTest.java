package com.example.demo.service.impl;

import com.example.demo.entity.ProductCategoryEntity;
import com.example.demo.exception.ProductCategoryAlreadyExistException;
import com.example.demo.exception.ProductCategoryNotFoundException;
import com.example.demo.repository.ProductCategoryRepository;
import com.example.demo.service.ProductCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductCategoryServiceImplTest {
    ProductCategoryRepository productCategoryRepository;
    ProductCategoryService productCategoryService;

    @BeforeEach
    void setUp() {
        productCategoryRepository = mock(ProductCategoryRepository.class);
        productCategoryService = new ProductCategoryServiceImpl(productCategoryRepository);
    }

    @Test
    void addNewCategory() throws Exception {
        String title = "test";

        ProductCategoryEntity category = new ProductCategoryEntity();
        category.setTitle(title);
        when(productCategoryRepository.save(category)).thenReturn(category);
        when(productCategoryRepository.findByTitle(title)).thenReturn(category);

        productCategoryService.addNewCategory(title);

        when(productCategoryRepository.existsByTitle(title)).thenReturn(true);
        assertThatExceptionOfType(ProductCategoryAlreadyExistException.class)
                .isThrownBy(() -> productCategoryService.addNewCategory(title));

        ProductCategoryEntity categoryEntity = productCategoryRepository.findByTitle(title);
        assertThat(categoryEntity.getTitle()).isEqualTo(category.getTitle());
    }

    @Test
    void getAll() {
        ProductCategoryEntity category = new ProductCategoryEntity();
        when(productCategoryRepository.save(category)).thenReturn(category);

        when(productCategoryRepository.findAll()).thenReturn(List.of(category));

        List<ProductCategoryEntity> allCategories = productCategoryService.getAll();
        assertThat(allCategories.size()).isEqualTo(1);
    }

    @Test
    void deleteCategory() throws Exception {
        Long id = 1L;
        ProductCategoryEntity category = new ProductCategoryEntity();
        category.setId(id);

        when(productCategoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(productCategoryRepository.findById(123L)).thenReturn(Optional.empty());

        productCategoryService.deleteCategory(id);

        assertThatExceptionOfType(ProductCategoryNotFoundException.class)
                .isThrownBy(() -> productCategoryService.deleteCategory(123L));
    }
}