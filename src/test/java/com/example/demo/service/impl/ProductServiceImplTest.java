package com.example.demo.service.impl;

import com.example.demo.entity.ProductEntity;
import com.example.demo.exception.ProductAlreadyExistException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShopInventoryRepository;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {
    ProductRepository productRepository;
    ShopInventoryRepository shopInventoryRepository;
    ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        shopInventoryRepository = mock(ShopInventoryRepository.class);
        productService = new ProductServiceImpl(productRepository, shopInventoryRepository);
    }

    @Test
    void addNewProduct() throws Exception {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setTitle("test");
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productRepository.findByTitle(productEntity.getTitle())).thenReturn(productEntity);
        when(productRepository.existsByTitle(productEntity.getTitle())).thenReturn(false);

        productService.addNewProduct(productEntity);

        when(productRepository.existsByTitle(productEntity.getTitle())).thenReturn(true);

        assertThatExceptionOfType(ProductAlreadyExistException.class)
                .isThrownBy(() -> productService.addNewProduct(productEntity));

        ProductEntity added = productRepository.findByTitle(productEntity.getTitle());
        assertThat(added.getId()).isEqualTo(productEntity.getId());
        assertThat(added.getTitle()).isEqualTo(productEntity.getTitle());
    }

    @Test
    void updateProduct() throws Exception {
        ProductEntity productEntityAbsent = new ProductEntity();
        productEntityAbsent.setId(12L);
        productEntityAbsent.setTitle("absent");

        ProductEntity productEntityPresent = new ProductEntity();
        productEntityPresent.setId(1L);
        productEntityPresent.setTitle("present");
        when(productRepository.save(productEntityPresent)).thenReturn(productEntityPresent);
        when(productRepository.findByTitle(productEntityPresent.getTitle())).thenReturn(productEntityPresent);
        when(productRepository.existsByTitle(productEntityPresent.getTitle())).thenReturn(false);

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> productService.updateProduct(productEntityAbsent.getTitle(), productEntityAbsent));

        productService.updateProduct(productEntityPresent.getTitle(), productEntityPresent);
    }

    @Test
    void getAll() {
        ProductEntity productEntity = new ProductEntity();
        when(productRepository.save(productEntity)).thenReturn(productEntity);

        when(productRepository.findAll()).thenReturn(List.of(productEntity));

        List<ProductEntity> allProducts = productService.getAll();
        assertThat(allProducts.size()).isEqualTo(1);
    }

    @Test
    void getProductByTitle() throws Exception {
        ProductEntity productEntity = new ProductEntity();
        Long id = 1L;
        productEntity.setId(id);
        String title = "test";
        productEntity.setTitle(title);
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productRepository.findByTitle(title)).thenReturn(productEntity);

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> productService.getProductByTitle("wrong title"));

        ProductEntity productByTitle = productService.getProductByTitle(title);

        assertThat(productByTitle.getId()).isEqualTo(id);
        assertThat(productByTitle.getTitle()).isEqualTo(productEntity.getTitle());
    }

    @Test
    void getProductById() throws Exception {
        ProductEntity productEntity = new ProductEntity();
        Long id = 1L;
        productEntity.setId(id);
        String title = "test";
        productEntity.setTitle(title);
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productRepository.findById(id)).thenReturn(Optional.of(productEntity));

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> productService.getProductById(123L));

        ProductEntity productById = productService.getProductById(id);

        assertThat(productById.getId()).isEqualTo(id);
        assertThat(productById.getTitle()).isEqualTo(productEntity.getTitle());
    }

    @Test
    void deleteProduct() throws Exception {
        Long id = 1L;
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(productEntity));
        when(productRepository.findById(123L)).thenReturn(Optional.empty());

        productService.deleteProduct(id);

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> productService.deleteProduct(123L));
    }
}