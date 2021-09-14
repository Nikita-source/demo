package com.example.demo.repository;

import com.example.demo.entity.ShopEntity;
import com.example.demo.entity.ShopInventoryEntity;
import com.example.demo.entity.WarehouseEntity;
import com.example.demo.entity.WarehouseInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long> {
    //WarehouseEntity findByInventories(Set<WarehouseInventoryEntity> inventories);
}
