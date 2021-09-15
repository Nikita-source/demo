package com.example.demo.controller;

import com.example.demo.dto.MessageResponse;
import com.example.demo.dto.WarehouseDto;
import com.example.demo.dto.WarehouseInventoryDto;
import com.example.demo.entity.WarehouseEntity;
import com.example.demo.entity.WarehouseInventoryEntity;
import com.example.demo.exception.*;
import com.example.demo.service.impl.WarehouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    private final WarehouseServiceImpl warehouseService;

    @Autowired
    public WarehouseController(WarehouseServiceImpl warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<?> addWarehouse(@RequestBody WarehouseDto warehouseDto) {
        try {
            warehouseService.addNewWarehouse(warehouseDto);
            return ResponseEntity.ok(new MessageResponse("Warehouse CREATED"));
        } catch (WarehouseAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("all-warehouses")
    public ResponseEntity<?> getAllWarehouses() {
        try {
            List<WarehouseEntity> warehouses = warehouseService.getAllWarehouses();
            List<WarehouseDto> result = new ArrayList<>();
            for (WarehouseEntity warehouse : warehouses) {
                WarehouseDto warehouseDto = WarehouseDto.fromWarehouse(warehouse);
                result.add(warehouseDto);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getWarehouseById(@PathVariable String id) {
        try {
            WarehouseEntity warehouse = warehouseService.getWarehouseById(Long.valueOf(id));
            WarehouseDto result = WarehouseDto.fromWarehouse(warehouse);
            return ResponseEntity.ok(result);
        } catch (WarehouseNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable String id) {
        try {
            warehouseService.deleteWarehouse(Long.valueOf(id));
            return ResponseEntity.ok("Warehouse with ID " + id + " deleted");
        } catch (WarehouseNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @PostMapping("inventory")
    public ResponseEntity<?> addWarehouseInventory(@RequestBody WarehouseInventoryDto warehouseInventoryDto) {
        try {
            warehouseService.addNewWarehouseInventory(warehouseInventoryDto);
            return ResponseEntity.ok(new MessageResponse("Warehouse Inventory CREATED"));
        } catch (InventoryAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @PutMapping("inventory/{id}")
    public ResponseEntity<?> updateWarehouseInventory(@PathVariable String id, @RequestBody WarehouseInventoryDto warehouseInventoryDto) {
        try {
            warehouseService.updateWarehouseInventory(Long.valueOf(id), warehouseInventoryDto);
            return ResponseEntity.ok(new MessageResponse("Warehouse Inventory UPDATED"));
        } catch (InventoryAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (InventoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (WarehouseNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("inventory/by-warehouse/{id}")
    public ResponseEntity<?> getAllInventoryByWarehouse(@PathVariable String id) {
        try {
            List<WarehouseInventoryEntity> warehouseInventories = warehouseService.getAllInventoryByWarehouse(Long.valueOf(id));
            List<WarehouseInventoryDto> result = new ArrayList<>();
            for (WarehouseInventoryEntity warehouseInventory : warehouseInventories) {
                WarehouseInventoryDto customerDto = WarehouseInventoryDto.fromWarehouseInventory(warehouseInventory);
                result.add(customerDto);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @DeleteMapping("inventory/{id}")
    public ResponseEntity<?> deleteWarehouseInventory(@PathVariable String id) {
        try {
            warehouseService.deleteWarehouseInventory(Long.valueOf(id));
            return ResponseEntity.ok("Warehouse Inventory with ID " + id + " deleted");
        } catch (InventoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }
}
