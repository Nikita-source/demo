package com.example.demo.dto;

import com.example.demo.entity.AddressEntity;
import com.example.demo.entity.ShopEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopDto {
    private Long id;
    private String shopTitle;
    private AddressEntity shopAddress;

    public static ShopEntity toShop(ShopDto shopDto) {
        ShopEntity shop = new ShopEntity();
        shop.setId(shopDto.getId());
        shop.setTitle(shopDto.getShopTitle());
        shop.setAddress(shopDto.getShopAddress());
        return shop;
    }

    public static ShopDto fromShop(ShopEntity shop) {
        ShopDto shopDto = new ShopDto();
        shopDto.setId(shop.getId());
        shopDto.setShopTitle(shop.getTitle());
        shopDto.setShopAddress(shop.getAddress());
        return shopDto;
    }
}
