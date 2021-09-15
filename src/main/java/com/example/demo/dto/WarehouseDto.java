package com.example.demo.dto;

import com.example.demo.entity.AddressEntity;
import com.example.demo.entity.WarehouseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WarehouseDto {
    private Long id;
    private AddressEntity warehouseAddress;

    public static WarehouseEntity toWarehouse(WarehouseDto warehouseDto) {
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(warehouseDto.getId());
        warehouse.setAddress(warehouseDto.getWarehouseAddress());
        return warehouse;
    }

    public static WarehouseDto fromWarehouse(WarehouseEntity warehouse) {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setId(warehouse.getId());
        warehouseDto.setWarehouseAddress(warehouse.getAddress());
        return warehouseDto;
    }
}
