package com.example.demo.dto;

import com.example.demo.entity.ShopInventoryEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopInventoryDto {
    private Long id;
    private String shopTitle;
    private String productTitle;
    private Long count;

    public static ShopInventoryDto fromShopInventory(ShopInventoryEntity shopInventory) {
        ShopInventoryDto shopInventoryDto = new ShopInventoryDto();
        shopInventoryDto.setId(shopInventory.getId());
        shopInventoryDto.setShopTitle(shopInventory.getShop().getTitle());
        shopInventoryDto.setProductTitle(shopInventory.getProduct().getTitle());
        shopInventoryDto.setCount(shopInventory.getCount());
        return shopInventoryDto;
    }
}
