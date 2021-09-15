package com.example.demo.service;

import com.example.demo.dto.WarehouseDto;
import com.example.demo.dto.WarehouseInventoryDto;
import com.example.demo.entity.WarehouseEntity;
import com.example.demo.entity.WarehouseInventoryEntity;
import com.example.demo.exception.*;

import java.util.List;

public interface WarehouseService {
    void addNewWarehouse(WarehouseDto warehouseDto) throws WarehouseAlreadyExistException;

    List<WarehouseEntity> getAllWarehouses();

    WarehouseEntity getWarehouseById(Long id) throws WarehouseNotFoundException;

    void deleteWarehouse(Long id) throws WarehouseNotFoundException;

    void addNewWarehouseInventory(WarehouseInventoryDto warehouseInventoryDto) throws InventoryAlreadyExistException, WarehouseNotFoundException, ProductNotFoundException;

    void updateWarehouseInventory(Long id, WarehouseInventoryDto warehouseInventoryDto) throws InventoryAlreadyExistException, InventoryNotFoundException, ProductNotFoundException, WarehouseNotFoundException;

    List<WarehouseInventoryEntity> getAllInventoryByWarehouse(Long id);

    void deleteWarehouseInventory(Long id) throws InventoryNotFoundException;
}
