package com.example.demo.service;

import com.example.demo.dto.OrderDto;
import com.example.demo.entity.OrderEntity;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.ProductNotFoundException;

import java.util.List;

public interface OrderService {
    void addNewOrder(OrderDto orderDto) throws CustomerNotFoundException, ProductNotFoundException;

    List<OrderEntity> getAllOrdersByCustomer(String email);

    void deleteOrder(Long id) throws CustomerNotFoundException;
}
