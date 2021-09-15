package com.example.demo.service;

import com.example.demo.dto.ShopDto;
import com.example.demo.dto.ShopInventoryDto;
import com.example.demo.entity.ShopEntity;
import com.example.demo.entity.ShopInventoryEntity;
import com.example.demo.exception.*;

import java.util.List;

public interface ShopService {
    void addNewShop(ShopDto shopDto) throws ShopAlreadyExistException;

    void updateShop(String title, ShopDto shopDto) throws ShopAlreadyExistException, ShopNotFoundException;

    List<ShopEntity> getAllShops();

    ShopEntity getShopByTitle(String title) throws ShopNotFoundException;

    ShopEntity getShopById(Long id) throws ShopNotFoundException;

    void deleteShop(Long id) throws ShopNotFoundException;

    void addNewShopInventory(ShopInventoryDto shopInventoryDto) throws InventoryAlreadyExistException, ProductNotFoundException, ShopNotFoundException;

    void updateShopInventory(Long id, ShopInventoryDto shopInventoryDto) throws InventoryAlreadyExistException, InventoryNotFoundException, ProductNotFoundException, ShopNotFoundException;

    List<ShopInventoryEntity> getAllInventoryByShop(Long id);

    void deleteShopInventory(Long id) throws InventoryNotFoundException;
}
