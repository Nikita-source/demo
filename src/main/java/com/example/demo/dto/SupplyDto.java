package com.example.demo.dto;

import com.example.demo.entity.SupplyEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplyDto {
    private Long id;
    private Long warehouseId;
    private String shopTitle;
    private String productTitle;
    private Long count;
    private Timestamp date;

    public static SupplyEntity toSupply(SupplyDto supplyDto) {
        SupplyEntity supply = new SupplyEntity();
        supply.setId(supplyDto.getId());

        return supply;
    }

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
