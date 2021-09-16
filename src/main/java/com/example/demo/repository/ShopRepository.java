package com.example.demo.repository;

import com.example.demo.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
    ShopEntity findByTitle(String title);

    Boolean existsByTitle(String title);
}
