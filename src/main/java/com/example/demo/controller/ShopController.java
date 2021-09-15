package com.example.demo.controller;

import com.example.demo.dto.MessageResponse;
import com.example.demo.dto.ShopDto;
import com.example.demo.dto.ShopInventoryDto;
import com.example.demo.entity.ShopEntity;
import com.example.demo.entity.ShopInventoryEntity;
import com.example.demo.exception.*;
import com.example.demo.service.impl.ShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {
    private final ShopServiceImpl shopService;

    @Autowired
    public ShopController(ShopServiceImpl shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    public ResponseEntity<?> addShop(@RequestBody ShopDto shopDto) {
        try {
            shopService.addNewShop(shopDto);
            return ResponseEntity.ok(new MessageResponse("Shop CREATED"));
        } catch (ShopAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @PutMapping("/{title}")
    public ResponseEntity<?> updateShop(@PathVariable String title, @RequestBody ShopDto shopDto) {
        try {
            shopService.updateShop(title, shopDto);
            return ResponseEntity.ok(new MessageResponse("Shop UPDATED"));
        } catch (ShopAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ShopNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("all-shops")
    public ResponseEntity<?> getAllShops() {
        try {
            List<ShopEntity> shops = shopService.getAllShops();
            List<ShopDto> result = new ArrayList<>();
            for (ShopEntity shop : shops) {
                ShopDto shopDto = ShopDto.fromShop(shop);
                result.add(shopDto);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getShopById(@PathVariable String id) {
        try {
            ShopEntity shop = shopService.getShopById(Long.valueOf(id));
            ShopDto result = ShopDto.fromShop(shop);
            return ResponseEntity.ok(result);
        } catch (ShopNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("by-title/{title}")
    public ResponseEntity<?> getShopByTitle(@PathVariable String title) {
        try {
            ShopEntity shop = shopService.getShopByTitle(title);
            ShopDto result = ShopDto.fromShop(shop);
            return ResponseEntity.ok(result);
        } catch (ShopNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteShop(@PathVariable String id) {
        try {
            shopService.deleteShop(Long.valueOf(id));
            return ResponseEntity.ok("Shop with ID " + id + " deleted");
        } catch (ShopNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @PostMapping("inventory")
    public ResponseEntity<?> addShopInventory(@RequestBody ShopInventoryDto shopInventoryDto) {
        try {
            shopService.addNewShopInventory(shopInventoryDto);
            return ResponseEntity.ok(new MessageResponse("Shop Inventory CREATED"));
        } catch (InventoryAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @PutMapping("inventory/{id}")
    public ResponseEntity<?> updateShopInventory(@PathVariable String id, @RequestBody ShopInventoryDto shopInventoryDto) {
        try {
            shopService.updateShopInventory(Long.valueOf(id), shopInventoryDto);
            return ResponseEntity.ok(new MessageResponse("Shop Inventory UPDATED"));
        } catch (InventoryAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (InventoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ShopNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("inventory/by-shop/{id}")
    public ResponseEntity<?> getAllInventoryByShop(@PathVariable String id) {
        try {
            List<ShopInventoryEntity> shopInventories = shopService.getAllInventoryByShop(Long.valueOf(id));
            List<ShopInventoryDto> result = new ArrayList<>();
            for (ShopInventoryEntity shopInventory : shopInventories) {
                ShopInventoryDto customerDto = ShopInventoryDto.fromShopInventory(shopInventory);
                result.add(customerDto);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @DeleteMapping("inventory/{id}")
    public ResponseEntity<?> deleteShopInventory(@PathVariable String id) {
        try {
            shopService.deleteShopInventory(Long.valueOf(id));
            return ResponseEntity.ok("Shop Inventory with ID " + id + " deleted");
        } catch (InventoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }
}
