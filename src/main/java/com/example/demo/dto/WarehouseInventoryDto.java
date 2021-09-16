package com.example.demo.dto;

import com.example.demo.entity.WarehouseInventoryEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WarehouseInventoryDto {
    private Long id;
    private Long warehouseId;
    private String productTitle;
    private Long count;

    public static WarehouseInventoryDto fromWarehouseInventory(WarehouseInventoryEntity warehouseInventory) {
        WarehouseInventoryDto warehouseInventoryDto = new WarehouseInventoryDto();
        warehouseInventoryDto.setId(warehouseInventory.getId());
        warehouseInventoryDto.setWarehouseId(warehouseInventory.getWarehouse().getId());
        warehouseInventoryDto.setProductTitle(warehouseInventory.getProduct().getTitle());
        warehouseInventoryDto.setCount(warehouseInventory.getCount());
        return warehouseInventoryDto;
    }
}
