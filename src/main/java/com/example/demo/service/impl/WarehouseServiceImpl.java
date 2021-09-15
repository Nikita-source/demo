package com.example.demo.service.impl;

import com.example.demo.dto.WarehouseDto;
import com.example.demo.dto.WarehouseInventoryDto;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.SupplyEntity;
import com.example.demo.entity.WarehouseEntity;
import com.example.demo.entity.WarehouseInventoryEntity;
import com.example.demo.exception.*;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.WarehouseInventoryRepository;
import com.example.demo.repository.WarehouseRepository;
import com.example.demo.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseInventoryRepository warehouseInventoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, WarehouseInventoryRepository warehouseInventoryRepository, ProductRepository productRepository) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseInventoryRepository = warehouseInventoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addNewWarehouse(WarehouseDto warehouseDto) throws WarehouseAlreadyExistException {
        if (warehouseRepository.findById(warehouseDto.getId()).isPresent()) {
            throw new WarehouseAlreadyExistException("Warehouse with this ID already exists");
        }
        WarehouseEntity warehouse = WarehouseDto.toWarehouse(warehouseDto);
        Set<SupplyEntity> supplies = new HashSet<>();
        warehouse.setSupplies(supplies);
        Set<WarehouseInventoryEntity> inventories = new HashSet<>();
        warehouse.setInventories(inventories);
        warehouseRepository.save(warehouse);
    }

    @Override
    public List<WarehouseEntity> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    @Override
    public WarehouseEntity getWarehouseById(Long id) throws WarehouseNotFoundException {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse with this ID not found"));
    }

    @Override
    public void deleteWarehouse(Long id) throws WarehouseNotFoundException {
        warehouseRepository.findById(id)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse with this ID not found"));
        warehouseRepository.deleteById(id);
    }

    @Override
    public void addNewWarehouseInventory(WarehouseInventoryDto warehouseInventoryDto) throws InventoryAlreadyExistException, WarehouseNotFoundException, ProductNotFoundException {
        ProductEntity product = productRepository.findByTitle(warehouseInventoryDto.getProductTitle());
        WarehouseEntity warehouse = warehouseRepository.findById(warehouseInventoryDto.getWarehouseId())
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse with this ID not found"));
        if (product == null) {
            throw new ProductNotFoundException("Product with this Title not found");
        }
        if (warehouseInventoryRepository.existsByWarehouseAndProduct(warehouse, product)) {
            throw new InventoryAlreadyExistException("Warehouse Inventory with this params already exist");
        }
        WarehouseInventoryEntity warehouseInventory = new WarehouseInventoryEntity();
        warehouseInventory.setCount(warehouseInventoryDto.getCount());
        warehouseInventory.setProduct(product);
        warehouseInventory.setWarehouse(warehouse);
        warehouseInventoryRepository.save(warehouseInventory);
    }

    @Override
    public void updateWarehouseInventory(Long id, WarehouseInventoryDto warehouseInventoryDto) throws InventoryAlreadyExistException, InventoryNotFoundException, ProductNotFoundException, WarehouseNotFoundException {
        WarehouseInventoryEntity warehouseInventoryEntity = warehouseInventoryRepository.findById(id).orElseThrow(() -> new InventoryNotFoundException("Warehouse Inventory with this ID not found"));
        ProductEntity product = productRepository.findByTitle(warehouseInventoryDto.getProductTitle());
        WarehouseEntity warehouse = warehouseRepository.findById(warehouseInventoryDto.getWarehouseId())
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse with this ID not found"));
        if (product == null) {
            throw new ProductNotFoundException("Product with this Title not found");
        }
        if (!warehouseInventoryEntity.getProduct().getTitle().equals(warehouseInventoryDto.getProductTitle())
                || !warehouseInventoryEntity.getWarehouse().getId().equals(warehouseInventoryDto.getWarehouseId())
                && warehouseInventoryRepository.existsByWarehouseAndProduct(warehouse, product)) {
            throw new InventoryAlreadyExistException("Warehouse Inventory with this params already exist");
        }
        warehouseInventoryEntity.setCount(warehouseInventoryDto.getCount());
        warehouseInventoryEntity.setProduct(product);
        warehouseInventoryEntity.setWarehouse(warehouse);
        warehouseInventoryRepository.save(warehouseInventoryEntity);
    }

    @Override
    public List<WarehouseInventoryEntity> getAllInventoryByWarehouse(Long id) {
        return warehouseInventoryRepository.findAllByWarehouse_Id(id);
    }

    @Override
    public void deleteWarehouseInventory(Long id) throws InventoryNotFoundException {
        warehouseInventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Warehouse Inventory with this ID not found"));
        warehouseInventoryRepository.deleteById(id);
    }
}
