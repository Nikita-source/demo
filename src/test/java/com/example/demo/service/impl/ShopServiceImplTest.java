package com.example.demo.service.impl;

import com.example.demo.dto.ShopDto;
import com.example.demo.dto.ShopInventoryDto;
import com.example.demo.entity.AddressEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.ShopEntity;
import com.example.demo.entity.ShopInventoryEntity;
import com.example.demo.exception.InventoryNotFoundException;
import com.example.demo.exception.ShopNotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShopInventoryRepository;
import com.example.demo.repository.ShopRepository;
import com.example.demo.service.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShopServiceImplTest {
    ShopRepository shopRepository;
    ShopInventoryRepository shopInventoryRepository;
    ProductRepository productRepository;
    ShopService shopService;

    @BeforeEach
    void setUp() {
        shopRepository = mock(ShopRepository.class);
        shopInventoryRepository = mock(ShopInventoryRepository.class);
        productRepository = mock(ProductRepository.class);
        shopService = new ShopServiceImpl(shopRepository, shopInventoryRepository, productRepository);
    }

    @Test
    void addNewShop() throws Exception {
        ShopDto shopDto = new ShopDto(1L, "test", new AddressEntity());

        ShopEntity shopEntity = ShopDto.toShop(shopDto);
        when(shopRepository.save(shopEntity)).thenReturn(shopEntity);
        when(shopRepository.findByTitle(shopDto.getShopTitle())).thenReturn(null);

        shopService.addNewShop(shopDto);

        when(shopRepository.findByTitle(shopDto.getShopTitle())).thenReturn(shopEntity);
        ShopEntity added = shopRepository.findByTitle(shopDto.getShopTitle());
        assertThat(added.getId()).isEqualTo(shopEntity.getId());
        assertThat(added.getTitle()).isEqualTo(shopEntity.getTitle());
    }

    @Test
    void updateShop() throws Exception {
        ShopDto shopDtoPresent = new ShopDto(1L, "present", new AddressEntity());
        ShopDto shopDtoAbsent = new ShopDto(2L, "absent", new AddressEntity());

        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setTitle("new");
        when(shopRepository.save(shopEntity)).thenReturn(shopEntity);
        when(shopRepository.findByTitle(shopDtoPresent.getShopTitle())).thenReturn(shopEntity);
        when(shopRepository.findByTitle(shopDtoAbsent.getShopTitle())).thenReturn(null);

        assertThatExceptionOfType(ShopNotFoundException.class)
                .isThrownBy(() -> shopService.updateShop(shopDtoAbsent.getShopTitle(), shopDtoAbsent));

        shopService.updateShop(shopDtoPresent.getShopTitle(), shopDtoPresent);
    }

    @Test
    void getAllShops() {
        ShopEntity shopEntity = new ShopEntity();
        when(shopRepository.save(shopEntity)).thenReturn(shopEntity);

        when(shopRepository.findAll()).thenReturn(List.of(shopEntity));

        List<ShopEntity> allShops = shopService.getAllShops();
        assertThat(allShops.size()).isEqualTo(1);
    }

    @Test
    void getShopByTitle() throws Exception {
        ShopEntity shopEntity = new ShopEntity();
        Long id = 1L;
        shopEntity.setId(id);
        String title = "test";
        shopEntity.setTitle(title);
        when(shopRepository.save(shopEntity)).thenReturn(shopEntity);
        when(shopRepository.findByTitle(title)).thenReturn(shopEntity);

        assertThatExceptionOfType(ShopNotFoundException.class)
                .isThrownBy(() -> shopService.getShopByTitle("wrong title"));

        ShopEntity shopByTitle = shopService.getShopByTitle(title);

        assertThat(shopByTitle.getId()).isEqualTo(id);
        assertThat(shopByTitle.getTitle()).isEqualTo(shopEntity.getTitle());
    }

    @Test
    void getShopById() throws Exception {
        ShopEntity shopEntity = new ShopEntity();
        Long id = 1L;
        shopEntity.setId(id);
        String title = "test";
        shopEntity.setTitle(title);
        when(shopRepository.save(shopEntity)).thenReturn(shopEntity);
        when(shopRepository.findById(id)).thenReturn(Optional.of(shopEntity));

        assertThatExceptionOfType(ShopNotFoundException.class)
                .isThrownBy(() -> shopService.getShopById(123L));

        ShopEntity shopById = shopService.getShopById(id);

        assertThat(shopById.getId()).isEqualTo(id);
        assertThat(shopById.getTitle()).isEqualTo(shopEntity.getTitle());
    }

    @Test
    void deleteShop() throws Exception {
        Long id = 1L;
        ShopEntity shop = new ShopEntity();
        shop.setId(id);

        when(shopRepository.findById(id)).thenReturn(Optional.of(shop));
        when(shopRepository.findById(123L)).thenReturn(Optional.empty());

        shopService.deleteShop(id);

        assertThatExceptionOfType(ShopNotFoundException.class)
                .isThrownBy(() -> shopService.deleteShop(123L));
    }

    @Test
    void addNewShopInventory() throws Exception {
        ShopInventoryDto shopInventoryDto = new ShopInventoryDto(1L, "test", "test", 10L);

        ProductEntity product = new ProductEntity();
        product.setTitle("test");
        when(productRepository.findByTitle(shopInventoryDto.getProductTitle())).thenReturn(product);

        ShopEntity shop = new ShopEntity();
        shop.setTitle("test");
        when(shopRepository.findByTitle(shopInventoryDto.getShopTitle())).thenReturn(shop);

        ShopInventoryEntity shopInventoryEntity = new ShopInventoryEntity();
        shopInventoryEntity.setShop(shop);
        shopInventoryEntity.setProduct(product);
        when(shopInventoryRepository.save(shopInventoryEntity)).thenReturn(shopInventoryEntity);
        when(shopInventoryRepository.existsByShopAndProduct(shop, product)).thenReturn(false);
        when(shopInventoryRepository.findByProduct_TitleAndShop_Title(product.getTitle(), shop.getTitle())).thenReturn(shopInventoryEntity);

        shopService.addNewShopInventory(shopInventoryDto);

        ShopInventoryEntity added = shopInventoryRepository.findByProduct_TitleAndShop_Title(product.getTitle(), shop.getTitle());
        assertThat(added.getProduct()).isEqualTo(product);
        assertThat(added.getShop()).isEqualTo(shop);
    }

    @Test
    void updateShopInventory() throws Exception {
        ShopInventoryDto shopInventoryDtoPresent = new ShopInventoryDto(1L, "present", "present", 10L);
        ShopInventoryDto shopInventoryDtoAbsent = new ShopInventoryDto(2L, "absent", "absent", 10L);

        ProductEntity product = new ProductEntity();
        product.setTitle(shopInventoryDtoPresent.getProductTitle());
        when(productRepository.findByTitle(shopInventoryDtoPresent.getProductTitle())).thenReturn(product);

        ShopEntity shop = new ShopEntity();
        shop.setTitle(shopInventoryDtoPresent.getProductTitle());
        when(shopRepository.findByTitle(shopInventoryDtoPresent.getShopTitle())).thenReturn(shop);

        ShopInventoryEntity shopInventoryEntity = new ShopInventoryEntity();
        shopInventoryEntity.setProduct(product);
        shopInventoryEntity.setShop(shop);
        when(shopInventoryRepository.save(shopInventoryEntity)).thenReturn(shopInventoryEntity);
        when(shopInventoryRepository.findById(shopInventoryDtoPresent.getId())).thenReturn(Optional.of(shopInventoryEntity));
        when(shopInventoryRepository.findById(shopInventoryDtoAbsent.getId())).thenReturn(Optional.empty());

        assertThatExceptionOfType(InventoryNotFoundException.class)
                .isThrownBy(() -> shopService.updateShopInventory(shopInventoryDtoAbsent.getId(), shopInventoryDtoAbsent));

        shopService.updateShopInventory(shopInventoryDtoPresent.getId(), shopInventoryDtoPresent);
    }

    @Test
    void getAllInventoryByShop() {
        Long id = 1L;
        ShopInventoryEntity shopInventoryEntity = new ShopInventoryEntity();
        when(shopInventoryRepository.save(shopInventoryEntity)).thenReturn(shopInventoryEntity);

        when(shopInventoryRepository.findAll()).thenReturn(List.of(shopInventoryEntity));
        when(shopInventoryRepository.findAllByShop_Id(id)).thenReturn(List.of(shopInventoryEntity));

        List<ShopInventoryEntity> allShopInventories = shopService.getAllInventoryByShop(id);
        assertThat(allShopInventories.size()).isEqualTo(1);
    }

    @Test
    void deleteShopInventory() throws Exception {
        Long id = 1L;
        ShopInventoryEntity shopInventoryEntity = new ShopInventoryEntity();

        shopInventoryEntity.setId(id);

        when(shopInventoryRepository.findById(id)).thenReturn(Optional.of(shopInventoryEntity));
        when(shopInventoryRepository.findById(123L)).thenReturn(Optional.empty());

        shopService.deleteShopInventory(id);

        assertThatExceptionOfType(InventoryNotFoundException.class)
                .isThrownBy(() -> shopService.deleteShopInventory(123L));
    }
}