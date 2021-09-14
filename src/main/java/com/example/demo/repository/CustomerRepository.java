package com.example.demo.repository;

import com.example.demo.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    CustomerEntity findByEmail(String email);

    CustomerEntity findByPhonenumber(String phonenumber);

    Boolean existsByEmail(String email);

    Boolean existsByPhonenumber(String phonenumber);
}
