package com.example.demo.service.impl;

import com.example.demo.dto.SupplyDto;
import com.example.demo.entity.*;
import com.example.demo.exception.*;
import com.example.demo.repository.*;
import com.example.demo.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyServiceImpl implements SupplyService {

    private final SupplyRepository supplyRepository;
    private final ShopRepository shopRepository;
    private final ShopInventoryRepository shopInventoryRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseInventoryRepository warehouseInventoryRepository;
    private final ProductServiceImpl productService;

    @Autowired
    public SupplyServiceImpl(SupplyRepository supplyRepository, ShopRepository shopRepository, ShopInventoryRepository shopInventoryRepository, ProductRepository productRepository, WarehouseRepository warehouseRepository, WarehouseInventoryRepository warehouseInventoryRepository, ProductServiceImpl productService) {
        this.supplyRepository = supplyRepository;
        this.shopRepository = shopRepository;
        this.shopInventoryRepository = shopInventoryRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.warehouseInventoryRepository = warehouseInventoryRepository;
        this.productService = productService;
    }

    @Override
    public void addNewSupply(SupplyDto supplyDto) throws SupplyException, ProductNotFoundException, WarehouseNotFoundException, ShopNotFoundException, InventoryAlreadyExistException, InventoryNotFoundException {
        WarehouseEntity warehouse = warehouseRepository.findById(supplyDto.getWarehouseId())
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse with this ID not found"));

        ShopEntity shop = shopRepository.findByTitle(supplyDto.getShopTitle());
        if (shop == null) {
            throw new ShopNotFoundException("Shop with this Title not found");
        }

        ProductEntity product = productRepository.findByTitle(supplyDto.getProductTitle());
        if (product == null) {
            throw new ProductNotFoundException("Product with this Title not found");
        }

        SupplyEntity supply = new SupplyEntity();
        supply.setWarehouse(warehouse);
        supply.setShop(shop);
        supply.setProduct(product);
        supply.setCount(supplyDto.getCount());
        supply.setDate(supplyDto.getDate());

        doSupply(supplyDto, warehouse, shop, product);

        supplyRepository.save(supply);
    }

    private void doSupply(SupplyDto supplyDto, WarehouseEntity warehouse, ShopEntity shop, ProductEntity product) throws SupplyException {
        WarehouseInventoryEntity currentWarehouseInventory = warehouseInventoryRepository.findByProduct_TitleAndWarehouse_Id(product.getTitle(), warehouse.getId());
        if (currentWarehouseInventory == null) {
            throw new SupplyException("WarehouseInventory with this params not found! PLEASE RESTOCK");
        }
        if (currentWarehouseInventory.getCount() < supplyDto.getCount()) {
            throw new SupplyException("Not enough stocks! PLEASE RESTOCK");
        }
        currentWarehouseInventory.setCount(currentWarehouseInventory.getCount() - supplyDto.getCount());
        warehouseInventoryRepository.save(currentWarehouseInventory);

        ShopInventoryEntity currentShopInventory = shopInventoryRepository.findByProduct_TitleAndShop_Title(product.getTitle(), shop.getTitle());
        if (currentShopInventory == null) {
            ShopInventoryEntity shopInventory = new ShopInventoryEntity();
            shopInventory.setCount(supplyDto.getCount());
            shopInventory.setProduct(product);
            shopInventory.setShop(shop);
            shopInventoryRepository.save(shopInventory);
        } else {
            currentShopInventory.setCount(currentShopInventory.getCount() + supplyDto.getCount());
            shopInventoryRepository.save(currentShopInventory);
        }

        productService.refreshProducts();
    }

    @Override
    public List<SupplyEntity> getAll() {
        return supplyRepository.findAll();
    }
}
