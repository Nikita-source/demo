package com.example.demo.controller;

import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.MessageResponse;
import com.example.demo.dto.OrderDto;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderServiceImpl orderService;

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody OrderDto orderDto) {
        try {
            orderService.addNewOrder(orderDto);
            return ResponseEntity.ok(new MessageResponse("Order CREATED"));
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("all-orders/{email}")
    public ResponseEntity<?> getAllOrders(@PathVariable String email) {
        try {
            List<OrderEntity> orders = orderService.getAllOrdersByCustomer(email);
            List<OrderDto> result = new ArrayList<>();
            for (OrderEntity order : orders) {
                OrderDto orderDto = OrderDto.fromOrder(order);
                result.add(orderDto);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        try {
            orderService.deleteOrder(Long.valueOf(id));
            return ResponseEntity.ok("Order with ID " + id + " deleted");
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }
}
