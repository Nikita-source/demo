package com.example.demo.repository;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByCustomer_Id(Long customer_id);

    List<OrderEntity> findAllByCustomer_Email(String customer_email);

    List<OrderEntity> findAllByProduct_Id(Long product_id);

    List<OrderEntity> findAllByProduct_Title(String product_title);
}
