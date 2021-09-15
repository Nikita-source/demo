package com.example.demo.service;

import com.example.demo.dto.SupplyDto;
import com.example.demo.entity.SupplyEntity;
import com.example.demo.exception.*;

import java.util.List;

public interface SupplyService {
    void addNewSupply(SupplyDto supplyDto) throws SupplyException, ProductNotFoundException, WarehouseNotFoundException, ShopNotFoundException, InventoryAlreadyExistException, InventoryNotFoundException;

    List<SupplyEntity> getAll();
}
