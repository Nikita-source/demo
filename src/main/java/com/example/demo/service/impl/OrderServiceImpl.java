package com.example.demo.service.impl;

import com.example.demo.dto.OrderDto;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void addNewOrder(OrderDto orderDto) throws CustomerNotFoundException, ProductNotFoundException {
        if (!productRepository.existsByTitle(orderDto.getProductTitle())) {
            throw new ProductNotFoundException("Product with this Title not found");
        }
        if (!customerRepository.existsByEmail(orderDto.getCustomerEmail())) {
            throw new CustomerNotFoundException("Customer with this Email not found");
        }
        OrderEntity order = new OrderEntity();
        CustomerEntity customer=customerRepository.findByEmail(orderDto.getCustomerEmail());
        ProductEntity product = productRepository.findByTitle(orderDto.getProductTitle());
        order.setCustomer(customer);
        order.setProduct(product);
        orderRepository.save(order);
    }

    @Override
    public List<OrderEntity> getAllOrdersByCustomer(String email) {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long id) throws CustomerNotFoundException {
        orderRepository.findById(id).
                orElseThrow(() -> new CustomerNotFoundException("Order with this ID not found"));
        orderRepository.deleteById(id);
    }
}
