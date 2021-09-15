package com.example.demo.repository;

import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.WarehouseEntity;
import com.example.demo.entity.WarehouseInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseInventoryRepository extends JpaRepository<WarehouseInventoryEntity, Long> {
    List<WarehouseInventoryEntity> findAllByProduct_Id(Long product_id);

    List<WarehouseInventoryEntity> findAllByProduct_Title(String product_title);

    List<WarehouseInventoryEntity> findAllByWarehouse_Id(Long warehouse_id);

    WarehouseInventoryEntity findByProduct_TitleAndWarehouse_Id(String product_title, Long warehouse_id);

    Boolean existsByWarehouseAndProduct(WarehouseEntity warehouse, ProductEntity product);
}
