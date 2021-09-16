package com.example.demo.dto;

import com.example.demo.entity.SupplyEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplyDto {
    private Long id;
    private Long warehouseId;
    private String shopTitle;
    private String productTitle;
    private Long count;
    private Timestamp date;

    public static SupplyDto fromSupply(SupplyEntity supply) {
        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setId(supply.getId());
        supplyDto.setWarehouseId(supply.getWarehouse().getId());
        supplyDto.setShopTitle(supply.getShop().getTitle());
        supplyDto.setProductTitle(supply.getProduct().getTitle());
        supplyDto.setCount(supply.getCount());
        supplyDto.setDate(supply.getDate());
        return supplyDto;
    }
}
