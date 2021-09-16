package com.example.demo.dto;

import com.example.demo.entity.OrderEntity;
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
public class OrderDto {
    private Long id;
    private String productTitle;
    private String customerEmail;

    public static OrderDto fromOrder(OrderEntity order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCustomerEmail(order.getCustomer().getEmail());
        orderDto.setProductTitle(order.getProduct().getTitle());
        return orderDto;
    }
}
