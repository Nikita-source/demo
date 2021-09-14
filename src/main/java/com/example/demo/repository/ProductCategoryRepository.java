package com.example.demo.repository;

import com.example.demo.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {
    ProductCategoryEntity findByTitle(String title);

    Boolean existsByTitle(String title);
}
