package com.example.demo.repository;

import com.example.demo.entity.ShopEntity;
import com.example.demo.entity.ShopInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
    ShopEntity findByTitle(String title);

    //ShopEntity findByInventories(Set<ShopInventoryEntity> inventories);
}
