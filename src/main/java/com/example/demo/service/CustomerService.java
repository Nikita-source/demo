package com.example.demo.service;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.exception.CustomerAlreadyExistException;
import com.example.demo.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    void addNewCustomer(CustomerDto customerDto) throws CustomerAlreadyExistException;

    void updateCustomer(String email, CustomerDto customerDto) throws CustomerAlreadyExistException, CustomerNotFoundException;

    List<CustomerEntity> getAll();

    CustomerEntity getCustomerByEmail(String email) throws CustomerNotFoundException;

    CustomerEntity getCustomerByPhonenumber(String phonenumber) throws CustomerNotFoundException;

    CustomerEntity getCustomerById(Long id) throws CustomerNotFoundException;

    void deleteCustomer(Long id) throws CustomerNotFoundException;
}
