package com.example.demo.service.impl;

import com.example.demo.dto.WarehouseDto;
import com.example.demo.dto.WarehouseInventoryDto;
import com.example.demo.entity.AddressEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.WarehouseEntity;
import com.example.demo.entity.WarehouseInventoryEntity;
import com.example.demo.exception.InventoryNotFoundException;
import com.example.demo.exception.WarehouseNotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.WarehouseInventoryRepository;
import com.example.demo.repository.WarehouseRepository;
import com.example.demo.service.WarehouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WarehouseServiceImplTest {
    WarehouseRepository warehouseRepository;
    WarehouseInventoryRepository warehouseInventoryRepository;
    ProductRepository productRepository;
    WarehouseService warehouseService;

    @BeforeEach
    void setUp() {
        warehouseRepository = mock(WarehouseRepository.class);
        warehouseInventoryRepository = mock(WarehouseInventoryRepository.class);
        productRepository = mock(ProductRepository.class);
        warehouseService = new WarehouseServiceImpl(warehouseRepository, warehouseInventoryRepository, productRepository);
    }

    @Test
    void addNewWarehouse() throws Exception {
        WarehouseDto warehouseDto = new WarehouseDto(1L, new AddressEntity());

        WarehouseEntity warehouseEntity = WarehouseDto.toWarehouse(warehouseDto);
        when(warehouseRepository.save(warehouseEntity)).thenReturn(warehouseEntity);

        warehouseService.addNewWarehouse(warehouseDto);

        when(warehouseRepository.findById(warehouseDto.getId())).thenReturn(Optional.of(warehouseEntity));
        WarehouseEntity added = warehouseRepository.findById(warehouseDto.getId()).orElse(new WarehouseEntity());
        assertThat(added.getId()).isEqualTo(warehouseEntity.getId());
    }

    @Test
    void getAllWarehouses() {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        when(warehouseRepository.save(warehouseEntity)).thenReturn(warehouseEntity);

        when(warehouseRepository.findAll()).thenReturn(List.of(warehouseEntity));

        List<WarehouseEntity> allWarehouses = warehouseService.getAllWarehouses();
        assertThat(allWarehouses.size()).isEqualTo(1);
    }

    @Test
    void getWarehouseById() throws Exception {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        Long id = 1L;
        warehouseEntity.setId(id);

        when(warehouseRepository.save(warehouseEntity)).thenReturn(warehouseEntity);
        when(warehouseRepository.findById(id)).thenReturn(Optional.of(warehouseEntity));

        assertThatExceptionOfType(WarehouseNotFoundException.class)
                .isThrownBy(() -> warehouseService.getWarehouseById(123L));

        WarehouseEntity warehouseById = warehouseService.getWarehouseById(id);

        assertThat(warehouseById.getId()).isEqualTo(warehouseEntity.getId());
    }

    @Test
    void deleteWarehouse() throws Exception {
        Long id = 1L;
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(id);

        when(warehouseRepository.findById(id)).thenReturn(Optional.of(warehouse));
        when(warehouseRepository.findById(123L)).thenReturn(Optional.empty());

        warehouseService.deleteWarehouse(id);

        assertThatExceptionOfType(WarehouseNotFoundException.class)
                .isThrownBy(() -> warehouseService.deleteWarehouse(123L));
    }

    @Test
    void addNewWarehouseInventory() throws Exception {
        WarehouseInventoryDto warehouseInventoryDto = new WarehouseInventoryDto(1L, 1L, "test", 10L);

        ProductEntity product = new ProductEntity();
        product.setTitle(warehouseInventoryDto.getProductTitle());
        when(productRepository.findByTitle(warehouseInventoryDto.getProductTitle())).thenReturn(product);

        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(warehouseInventoryDto.getId());
        when(warehouseRepository.findById(warehouseInventoryDto.getWarehouseId())).thenReturn(Optional.of(warehouse));

        WarehouseInventoryEntity warehouseInventoryEntity = new WarehouseInventoryEntity();
        warehouseInventoryEntity.setWarehouse(warehouse);
        warehouseInventoryEntity.setProduct(product);
        when(warehouseInventoryRepository.save(warehouseInventoryEntity)).thenReturn(warehouseInventoryEntity);
        when(warehouseInventoryRepository.existsByWarehouseAndProduct(warehouse, product)).thenReturn(false);
        when(warehouseInventoryRepository.findByProduct_TitleAndWarehouse_Id(product.getTitle(), warehouse.getId())).thenReturn(warehouseInventoryEntity);

        warehouseService.addNewWarehouseInventory(warehouseInventoryDto);

        WarehouseInventoryEntity added = warehouseInventoryRepository.findByProduct_TitleAndWarehouse_Id(product.getTitle(), warehouse.getId());
        assertThat(added.getProduct()).isEqualTo(product);
        assertThat(added.getWarehouse()).isEqualTo(warehouse);
    }

    @Test
    void updateWarehouseInventory() throws Exception {
        WarehouseInventoryDto warehouseInventoryDtoPresent = new WarehouseInventoryDto(1L, 1L, "present", 10L);
        WarehouseInventoryDto warehouseInventoryDtoAbsent = new WarehouseInventoryDto(2L, 2L, "absent", 10L);

        ProductEntity product = new ProductEntity();
        product.setTitle(warehouseInventoryDtoPresent.getProductTitle());
        when(productRepository.findByTitle(warehouseInventoryDtoPresent.getProductTitle())).thenReturn(product);

        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(warehouseInventoryDtoPresent.getWarehouseId());
        when(warehouseRepository.findById(warehouseInventoryDtoPresent.getWarehouseId())).thenReturn(Optional.of(warehouse));

        WarehouseInventoryEntity warehouseInventoryEntity = new WarehouseInventoryEntity();
        warehouseInventoryEntity.setProduct(product);
        warehouseInventoryEntity.setWarehouse(warehouse);
        when(warehouseInventoryRepository.save(warehouseInventoryEntity)).thenReturn(warehouseInventoryEntity);
        when(warehouseInventoryRepository.findById(warehouseInventoryDtoPresent.getId())).thenReturn(Optional.of(warehouseInventoryEntity));
        when(warehouseInventoryRepository.findById(warehouseInventoryDtoAbsent.getId())).thenReturn(Optional.empty());

        assertThatExceptionOfType(InventoryNotFoundException.class)
                .isThrownBy(() -> warehouseService.updateWarehouseInventory(warehouseInventoryDtoAbsent.getId(), warehouseInventoryDtoAbsent));

        warehouseService.updateWarehouseInventory(warehouseInventoryDtoPresent.getId(), warehouseInventoryDtoPresent);
    }

    @Test
    void getAllInventoryByWarehouse() {
        Long id = 1L;
        WarehouseInventoryEntity warehouseInventoryEntity = new WarehouseInventoryEntity();
        when(warehouseInventoryRepository.save(warehouseInventoryEntity)).thenReturn(warehouseInventoryEntity);

        when(warehouseInventoryRepository.findAll()).thenReturn(List.of(warehouseInventoryEntity));
        when(warehouseInventoryRepository.findAllByWarehouse_Id(id)).thenReturn(List.of(warehouseInventoryEntity));

        List<WarehouseInventoryEntity> allWarehouseInventories = warehouseService.getAllInventoryByWarehouse(id);
        assertThat(allWarehouseInventories.size()).isEqualTo(1);
    }

    @Test
    void deleteWarehouseInventory() throws Exception {
        Long id = 1L;
        WarehouseInventoryEntity warehouseInventoryEntity = new WarehouseInventoryEntity();

        warehouseInventoryEntity.setId(id);

        when(warehouseInventoryRepository.findById(id)).thenReturn(Optional.of(warehouseInventoryEntity));
        when(warehouseInventoryRepository.findById(123L)).thenReturn(Optional.empty());

        warehouseService.deleteWarehouseInventory(id);

        assertThatExceptionOfType(InventoryNotFoundException.class)
                .isThrownBy(() -> warehouseService.deleteWarehouseInventory(123L));
    }
}