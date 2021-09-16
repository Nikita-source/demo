package com.example.demo.service.impl;

import com.example.demo.dto.OrderDto;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.ShopInventoryEntity;
import com.example.demo.exception.OrderException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShopInventoryRepository;
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {
    OrderRepository orderRepository;
    ProductRepository productRepository;
    CustomerRepository customerRepository;
    ShopInventoryRepository shopInventoryRepository;
    ProductServiceImpl productService;
    OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        productRepository = mock(ProductRepository.class);
        customerRepository = mock(CustomerRepository.class);
        shopInventoryRepository = mock(ShopInventoryRepository.class);
        productService = mock(ProductServiceImpl.class);
        orderService = new OrderServiceImpl(orderRepository, productRepository, customerRepository, shopInventoryRepository, productService);
    }

    @Test
    void addNewOrder() throws Exception {
        OrderDto orderDto = new OrderDto(1L, "test", "test");

        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setTitle("test");
        product.setAvailability(true);
        when(productRepository.findByTitle(orderDto.getProductTitle())).thenReturn(product);

        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setEmail("test");
        when(customerRepository.findByEmail(orderDto.getCustomerEmail())).thenReturn(customer);

        OrderEntity order = new OrderEntity();
        order.setCustomer(customer);
        order.setProduct(product);
        order.setId(orderDto.getId());
        when(orderRepository.save(order)).thenReturn(order);
        when(orderRepository.findById(orderDto.getId())).thenReturn(Optional.of(order));

        List<ShopInventoryEntity> shopInventories = new ArrayList<>();
        ShopInventoryEntity shopInventoryEntity = new ShopInventoryEntity();
        shopInventoryEntity.setCount(10L);
        shopInventories.add(shopInventoryEntity);
        when(shopInventoryRepository.findAllByProduct_Title(product.getTitle())).thenReturn(shopInventories);

        when(productRepository.existsByTitle(orderDto.getProductTitle())).thenReturn(true);
        when(customerRepository.existsByEmail(orderDto.getCustomerEmail())).thenReturn(true);

        orderService.addNewOrder(orderDto);

        OrderEntity orderEntity = orderRepository.findById(orderDto.getId()).orElse(new OrderEntity());
        assertThat(orderEntity.getCustomer()).isEqualTo(customer);
        assertThat(orderEntity.getProduct()).isEqualTo(product);
    }

    @Test
    void getAllOrdersByCustomer() {
        OrderEntity order = new OrderEntity();
        String customer = "test";
        when(orderRepository.save(order)).thenReturn(order);

        when(orderRepository.findAllByCustomer_Email(customer)).thenReturn(List.of(order));

        List<OrderEntity> allOrders = orderService.getAllOrdersByCustomer(customer);
        assertThat(allOrders.size()).isEqualTo(1);
    }

    @Test
    void deleteOrder() throws Exception {
        Long id = 1L;
        OrderEntity order = new OrderEntity();
        order.setId(id);

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(orderRepository.findById(123L)).thenReturn(Optional.empty());

        orderService.deleteOrder(id);

        assertThatExceptionOfType(OrderException.class)
                .isThrownBy(() -> orderService.deleteOrder(123L));
    }
}