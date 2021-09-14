package com.example.demo.repository;

import com.example.demo.entity.ShopInventoryEntity;
import com.example.demo.entity.WarehouseInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseInventoryRepository extends JpaRepository<WarehouseInventoryEntity, Long> {
    List<WarehouseInventoryEntity> findAllByProduct_Id(Long product_id);

    List<WarehouseInventoryEntity> findAllByProduct_Title(String product_title);

    List<WarehouseInventoryEntity> findByWarehouse_Id(Long warehouse_id);
}
