package com.example.demo.repository;

import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.ShopEntity;
import com.example.demo.entity.ShopInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopInventoryRepository extends JpaRepository<ShopInventoryEntity, Long> {

    List<ShopInventoryEntity> findAllByProduct_Title(String product_title);

    List<ShopInventoryEntity> findAllByShop_Id(Long shop_id);


    ShopInventoryEntity findByProduct_TitleAndShop_Title(String product_title, String shop_title);

    Boolean existsByShopAndProduct(ShopEntity shop, ProductEntity product);
}
