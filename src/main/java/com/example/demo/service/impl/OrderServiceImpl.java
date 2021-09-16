package com.example.demo.service.impl;

import com.example.demo.dto.OrderDto;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.ShopInventoryEntity;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.OrderException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShopInventoryRepository;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ShopInventoryRepository shopInventoryRepository;
    private final ProductServiceImpl productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository, ShopInventoryRepository shopInventoryRepository, ProductServiceImpl productService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.shopInventoryRepository = shopInventoryRepository;
        this.productService = productService;
    }

    @Override
    public void addNewOrder(OrderDto orderDto) throws CustomerNotFoundException, ProductNotFoundException, OrderException {
        if (!productRepository.existsByTitle(orderDto.getProductTitle())) {
            throw new ProductNotFoundException("Product with this Title not found");
        }
        if (!customerRepository.existsByEmail(orderDto.getCustomerEmail())) {
            throw new CustomerNotFoundException("Customer with this Email not found");
        }
        productService.refreshProducts();
        ProductEntity product = productRepository.findByTitle(orderDto.getProductTitle());
        if (!product.getAvailability()) {
            throw new OrderException("Not enough stocks!!");
        }

        OrderEntity order = new OrderEntity();
        CustomerEntity customer = customerRepository.findByEmail(orderDto.getCustomerEmail());
        order.setCustomer(customer);
        order.setProduct(product);

        doOrder(product);

        orderRepository.save(order);
    }

    private void doOrder(ProductEntity product) throws OrderException {
        List<ShopInventoryEntity> shopInventories = shopInventoryRepository.findAllByProduct_Title(product.getTitle());
        ShopInventoryEntity currentShopInventory = null;
        for (ShopInventoryEntity shopInventoryEntity : shopInventories) {
            if (shopInventoryEntity.getCount() != 0) {
                currentShopInventory = shopInventoryEntity;
                break;
            }
        }

        if (currentShopInventory == null) {
            productService.refreshProducts();
            throw new OrderException("Something went wrong!!");
        } else {
            currentShopInventory.setCount(currentShopInventory.getCount() - 1);
            shopInventoryRepository.save(currentShopInventory);
        }
    }

    @Override
    public List<OrderEntity> getAllOrdersByCustomer(String email) {
        return orderRepository.findAllByCustomer_Email(email);
    }

    @Override
    public void deleteOrder(Long id) throws OrderException {
        orderRepository.findById(id).
                orElseThrow(() -> new OrderException("Order with this ID not found"));
        orderRepository.deleteById(id);
    }
}
