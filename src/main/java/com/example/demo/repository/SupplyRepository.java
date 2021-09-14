package com.example.demo.repository;

import com.example.demo.entity.ShopInventoryEntity;
import com.example.demo.entity.SupplyEntity;
import com.example.demo.entity.WarehouseInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<SupplyEntity, Long> {
    List<SupplyEntity> findAllByProduct_Title(String product_title);

    List<SupplyEntity> findAllByProduct_Id(Long product_id);

    List<SupplyEntity> findAllByShop_Id(Long shop_id);

    List<SupplyEntity> findAllByShop_Title(String shop_title);

    List<SupplyEntity> findByWarehouse_Id(Long warehouse_id);
}
