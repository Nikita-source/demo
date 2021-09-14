package com.example.demo.service.impl;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.exception.CustomerAlreadyExistException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void addNewCustomer(CustomerDto customerDto) throws CustomerAlreadyExistException {
        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new CustomerAlreadyExistException("Customer with this Email already exists");
        }
        if (customerRepository.existsByPhonenumber(customerDto.getPhonenumber())) {
            throw new CustomerAlreadyExistException("Customer with this Phone Number already exists");
        }
        CustomerEntity customer = CustomerDto.toCustomer(customerDto);
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(String email, CustomerDto customerDto) throws CustomerAlreadyExistException, CustomerNotFoundException {
        CustomerEntity customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with this Email not found");
        }
        if (!customerDto.getEmail().equals(customer.getEmail()) && customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new CustomerAlreadyExistException("Customer with this Email already exists");
        }
        if (!customerDto.getPhonenumber().equals(customer.getPhonenumber()) &&customerRepository.existsByPhonenumber(customerDto.getPhonenumber())) {
            throw new CustomerAlreadyExistException("Customer with this Phone Number already exists");
        }
        customer.setEmail(customerDto.getEmail());
        customer.setPhonenumber(customerDto.getPhonenumber());
        customer.setName(customerDto.getName());
        customer.setSecondname(customerDto.getSecondname());
        customer.setLastname(customerDto.getLastname());
        customerRepository.save(customer);
    }

    @Override
    public List<CustomerEntity> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerEntity getCustomerByEmail(String email) throws CustomerNotFoundException {
        CustomerEntity customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with this Email not found");
        }
        return customer;
    }

    @Override
    public CustomerEntity getCustomerByPhonenumber(String phonenumber) throws CustomerNotFoundException {
        CustomerEntity customer = customerRepository.findByPhonenumber(phonenumber);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with this Phone Number not found");
        }
        return customer;
    }

    @Override
    public CustomerEntity getCustomerById(Long id) throws CustomerNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with this ID not found"));
    }

    @Override
    public void deleteCustomer(Long id) throws CustomerNotFoundException {
        customerRepository.findById(id).
                orElseThrow(() -> new CustomerNotFoundException("Customer with this ID not found"));
        customerRepository.deleteById(id);
    }
}
