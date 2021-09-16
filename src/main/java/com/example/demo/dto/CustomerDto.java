package com.example.demo.dto;

import com.example.demo.entity.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {
    private String email;
    private String phonenumber;
    private String name;
    private String secondname;
    private String lastname;

    public static CustomerEntity toCustomer(CustomerDto customerDto) {
        CustomerEntity customer = new CustomerEntity();
        customer.setEmail(customerDto.getEmail());
        customer.setPhonenumber(customerDto.getPhonenumber());
        customer.setName(customerDto.getName());
        customer.setSecondname(customerDto.getSecondname());
        customer.setLastname(customerDto.getLastname());
        return customer;
    }

    public static CustomerDto fromCustomer(CustomerEntity customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail(customer.getEmail());
        customerDto.setPhonenumber(customer.getPhonenumber());
        customerDto.setName(customer.getName());
        customerDto.setSecondname(customer.getSecondname());
        customerDto.setLastname(customer.getLastname());
        return customerDto;
    }
}