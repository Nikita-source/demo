package com.example.demo.repository;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.Role;
import com.example.demo.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    CustomerEntity findByEmail(String email);
}
