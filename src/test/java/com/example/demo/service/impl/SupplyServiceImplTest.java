package com.example.demo.service.impl;

import com.example.demo.dto.SupplyDto;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.SupplyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SupplyServiceImplTest {

    SupplyRepository supplyRepository;
    ShopRepository shopRepository;
    ShopInventoryRepository shopInventoryRepository;
    ProductRepository productRepository;
    WarehouseRepository warehouseRepository;
    WarehouseInventoryRepository warehouseInventoryRepository;
    ProductServiceImpl productService;
    SupplyService supplyService;

    @BeforeEach
    void setUp() {
        supplyRepository = mock(SupplyRepository.class);
        shopRepository = mock(ShopRepository.class);
        shopInventoryRepository = mock(ShopInventoryRepository.class);
        productRepository = mock(ProductRepository.class);
        warehouseRepository = mock(WarehouseRepository.class);
        warehouseInventoryRepository = mock(WarehouseInventoryRepository.class);
        productService = mock(ProductServiceImpl.class);
        supplyService = new SupplyServiceImpl(supplyRepository, shopRepository, shopInventoryRepository, productRepository, warehouseRepository, warehouseInventoryRepository, productService);
    }

    @Test
    void addNewSupply() throws Exception {
        SupplyDto supplyDto = new SupplyDto(1L, 1L, "test", "test", 10L, new Timestamp(System.currentTimeMillis()));

        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setTitle("test");
        product.setAvailability(true);
        when(productRepository.findByTitle(supplyDto.getProductTitle())).thenReturn(product);

        ShopEntity shop = new ShopEntity();
        shop.setId(1L);
        shop.setTitle("test");
        when(shopRepository.findByTitle(supplyDto.getShopTitle())).thenReturn(shop);

        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(1L);
        when(warehouseRepository.findById(supplyDto.getWarehouseId())).thenReturn(Optional.of(warehouse));

        SupplyEntity supply = new SupplyEntity();
        supply.setId(1L);
        supply.setWarehouse(warehouse);
        supply.setShop(shop);
        supply.setProduct(product);
        supply.setCount(1L);
        when(supplyRepository.save(supply)).thenReturn(supply);
        when(supplyRepository.findById(supply.getId())).thenReturn(Optional.of(supply));

        WarehouseInventoryEntity warehouseInventoryEntity = new WarehouseInventoryEntity();
        warehouseInventoryEntity.setCount(10L);
        when(warehouseInventoryRepository.findByProduct_TitleAndWarehouse_Id(product.getTitle(), warehouse.getId())).thenReturn(warehouseInventoryEntity);

        ShopInventoryEntity shopInventoryEntity = new ShopInventoryEntity();
        shopInventoryEntity.setCount(10L);
        when(shopInventoryRepository.findByProduct_TitleAndShop_Title(product.getTitle(), shop.getTitle())).thenReturn(shopInventoryEntity);

        supplyService.addNewSupply(supplyDto);

        SupplyEntity supplyEntity = supplyRepository.findById(supplyDto.getId()).orElse(new SupplyEntity());
        assertThat(supplyEntity.getShop()).isEqualTo(shop);
        assertThat(supplyEntity.getProduct()).isEqualTo(product);
        assertThat(supplyEntity.getWarehouse()).isEqualTo(warehouse);
    }

    @Test
    void getAll() {
        SupplyEntity supply = new SupplyEntity();

        when(supplyRepository.save(supply)).thenReturn(supply);

        when(supplyRepository.findAll()).thenReturn(List.of(supply));

        List<SupplyEntity> allSupplies = supplyService.getAll();
        assertThat(allSupplies.size()).isEqualTo(1);
    }
}