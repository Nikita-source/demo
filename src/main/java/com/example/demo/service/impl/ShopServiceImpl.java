package com.example.demo.service.impl;

import com.example.demo.dto.ShopDto;
import com.example.demo.dto.ShopInventoryDto;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.ShopEntity;
import com.example.demo.entity.ShopInventoryEntity;
import com.example.demo.entity.SupplyEntity;
import com.example.demo.exception.*;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShopInventoryRepository;
import com.example.demo.repository.ShopRepository;
import com.example.demo.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final ShopInventoryRepository shopInventoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, ShopInventoryRepository shopInventoryRepository, ProductRepository productRepository) {
        this.shopRepository = shopRepository;
        this.shopInventoryRepository = shopInventoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addNewShop(ShopDto shopDto) throws ShopAlreadyExistException {
        ShopEntity tmp = shopRepository.findByTitle(shopDto.getShopTitle());
        if (tmp != null) {
            throw new ShopAlreadyExistException("Shop with this Title already exists");
        }
        ShopEntity shop = ShopDto.toShop(shopDto);
        Set<SupplyEntity> supplies = new HashSet<>();
        shop.setSupplies(supplies);
        Set<ShopInventoryEntity> inventories = new HashSet<>();
        shop.setInventories(inventories);
        shopRepository.save(shop);
    }

    @Override
    public void updateShop(String title, ShopDto shopDto) throws ShopAlreadyExistException, ShopNotFoundException {
        ShopEntity shopEntity = shopRepository.findByTitle(title);
        if (shopEntity == null) {
            throw new ShopNotFoundException("Shop with this Title not found");
        }
        if (!shopDto.getShopTitle().equals(shopEntity.getTitle()) && shopRepository.existsByTitle(shopDto.getShopTitle())) {
            throw new ShopAlreadyExistException("Shop with this Title already exists");
        }
        shopEntity.setTitle(shopDto.getShopTitle());
        shopEntity.setAddress(shopDto.getShopAddress());
        shopRepository.save(shopEntity);
    }

    @Override
    public List<ShopEntity> getAllShops() {
        return shopRepository.findAll();
    }

    @Override
    public ShopEntity getShopByTitle(String title) throws ShopNotFoundException {
        ShopEntity shop = shopRepository.findByTitle(title);
        if (shop == null) {
            throw new ShopNotFoundException("Shop with this Title not found");
        }
        return shop;
    }

    @Override
    public ShopEntity getShopById(Long id) throws ShopNotFoundException {
        return shopRepository.findById(id)
                .orElseThrow(() -> new ShopNotFoundException("Shop with this ID not found"));
    }

    @Override
    public void deleteShop(Long id) throws ShopNotFoundException {
        shopRepository.findById(id)
                .orElseThrow(() -> new ShopNotFoundException("Shop with this ID not found"));
        shopRepository.deleteById(id);
    }

    @Override
    public void addNewShopInventory(ShopInventoryDto shopInventoryDto) throws InventoryAlreadyExistException, ProductNotFoundException, ShopNotFoundException {
        ProductEntity product = productRepository.findByTitle(shopInventoryDto.getProductTitle());
        ShopEntity shop = shopRepository.findByTitle(shopInventoryDto.getShopTitle());
        if (product == null) {
            throw new ProductNotFoundException("Product with this Title not found");
        }
        if (shop == null) {
            throw new ShopNotFoundException("Shop with this Title not found");
        }
        if (shopInventoryRepository.existsByShopAndProduct(shop, product)) {
            throw new InventoryAlreadyExistException("Shop Inventory with this params already exist");
        }
        ShopInventoryEntity shopInventory = new ShopInventoryEntity();
        shopInventory.setCount(shopInventoryDto.getCount());
        shopInventory.setProduct(product);
        shopInventory.setShop(shop);
        shopInventoryRepository.save(shopInventory);
    }

    @Override
    public void updateShopInventory(Long id, ShopInventoryDto shopInventoryDto) throws InventoryAlreadyExistException, InventoryNotFoundException, ProductNotFoundException, ShopNotFoundException {
        ShopInventoryEntity shopInventoryEntity = shopInventoryRepository.findById(id).orElseThrow(() -> new InventoryNotFoundException("Shop Inventory with this ID not found"));
        ProductEntity product = productRepository.findByTitle(shopInventoryDto.getProductTitle());
        ShopEntity shop = shopRepository.findByTitle(shopInventoryDto.getShopTitle());
        if (product == null) {
            throw new ProductNotFoundException("Product with this Title not found");
        }
        if (shop == null) {
            throw new ShopNotFoundException("Shop with this Title not found");
        }
        if (!shopInventoryEntity.getProduct().getTitle().equals(shopInventoryDto.getProductTitle())
                || !shopInventoryEntity.getShop().getTitle().equals(shopInventoryDto.getShopTitle())
                && shopInventoryRepository.existsByShopAndProduct(shop, product)) {
            throw new InventoryAlreadyExistException("Shop Inventory with this params already exist");
        }
        shopInventoryEntity.setCount(shopInventoryDto.getCount());
        shopInventoryEntity.setProduct(product);
        shopInventoryEntity.setShop(shop);
        shopInventoryRepository.save(shopInventoryEntity);
    }

    @Override
    public List<ShopInventoryEntity> getAllInventoryByShop(Long id) {
        return shopInventoryRepository.findAllByShop_Id(id);
    }

    @Override
    public void deleteShopInventory(Long id) throws InventoryNotFoundException {
        shopInventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Shop Inventory with this ID not found"));
        shopInventoryRepository.deleteById(id);
    }
}
