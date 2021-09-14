package com.example.demo.repository;

import com.example.demo.entity.ShopInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopInventoryRepository extends JpaRepository<ShopInventoryEntity, Long> {
    List<ShopInventoryEntity> findAllByProduct_Id(Long product_id);

    List<ShopInventoryEntity> findAllByProduct_Title(String product_title);

    List<ShopInventoryEntity> findAllByShop_Id(Long shop_id);

    List<ShopInventoryEntity> findAllByShop_Title(String shop_title);
}
