package com.example.demo.service.impl;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.exception.CustomerAlreadyExistException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    CustomerService customerService;
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void addNewCustomer() throws Exception {
        CustomerDto customerDto = new CustomerDto("test", "test", "test", "test", "test");

        CustomerEntity customerEntity = CustomerDto.toCustomer(customerDto);
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
        when(customerRepository.findByEmail(customerDto.getEmail())).thenReturn(customerEntity);

        customerService.addNewCustomer(customerDto);

        when(customerRepository.existsByEmail(customerDto.getEmail())).thenReturn(true);
        when(customerRepository.existsByPhonenumber(customerDto.getPhonenumber())).thenReturn(true);

        assertThatExceptionOfType(CustomerAlreadyExistException.class)
                .isThrownBy(() -> customerService.addNewCustomer(customerDto));

        CustomerEntity added = customerRepository.findByEmail(customerDto.getEmail());
        assertThat(added.getEmail()).isEqualTo(customerDto.getEmail());
        assertThat(added.getPhonenumber()).isEqualTo(customerDto.getPhonenumber());
    }

    @Test
    void updateCustomer() throws Exception {
        CustomerDto customerDtoPresent = new CustomerDto("present", "present", "present", "present", "present");
        CustomerDto customerDtoAbsent = new CustomerDto("absent", "absent", "absent", "absent", "absent");

        CustomerEntity customerEntity = new CustomerEntity();
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
        when(customerRepository.findByEmail(customerDtoPresent.getEmail())).thenReturn(customerEntity);
        when(customerRepository.findByEmail(customerDtoAbsent.getEmail())).thenReturn(null);

        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> customerService.updateCustomer(customerDtoAbsent.getEmail(), customerDtoAbsent));

        customerService.updateCustomer(customerDtoPresent.getEmail(), customerDtoPresent);
    }

    @Test
    void getAll() {
        CustomerEntity customerEntity = new CustomerEntity();
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);

        when(customerRepository.findAll()).thenReturn(List.of(customerEntity));

        List<CustomerEntity> allCustomers = customerService.getAll();
        assertThat(allCustomers.size()).isEqualTo(1);
    }

    @Test
    void getCustomerByEmail() throws Exception {
        CustomerEntity customerEntity = new CustomerEntity();
        Long id = 1L;
        customerEntity.setId(id);
        String email = "test";
        customerEntity.setEmail(email);
        String phonenumber = "test";
        customerEntity.setPhonenumber(phonenumber);
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
        when(customerRepository.findByEmail(email)).thenReturn(customerEntity);

        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> customerService.getCustomerByEmail("wrong email"));

        CustomerEntity customerByEmail = customerService.getCustomerByEmail(email);

        assertThat(customerByEmail.getId()).isEqualTo(id);
        assertThat(customerByEmail.getEmail()).isEqualTo(customerEntity.getEmail());
        assertThat(customerByEmail.getPhonenumber()).isEqualTo(customerEntity.getPhonenumber());
    }

    @Test
    void getCustomerByPhonenumber() throws Exception {
        CustomerEntity customerEntity = new CustomerEntity();
        Long id = 1L;
        customerEntity.setId(id);
        String email = "test";
        customerEntity.setEmail(email);
        String phonenumber = "test";
        customerEntity.setPhonenumber(phonenumber);
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
        when(customerRepository.findByPhonenumber(phonenumber)).thenReturn(customerEntity);

        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> customerService.getCustomerByPhonenumber("wrong phonenumber"));

        CustomerEntity customerByPhonenumber = customerService.getCustomerByPhonenumber(phonenumber);

        assertThat(customerByPhonenumber.getId()).isEqualTo(id);
        assertThat(customerByPhonenumber.getEmail()).isEqualTo(customerEntity.getEmail());
        assertThat(customerByPhonenumber.getPhonenumber()).isEqualTo(customerEntity.getPhonenumber());
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerEntity customerEntity = new CustomerEntity();
        Long id = 1L;
        customerEntity.setId(id);
        String email = "test";
        customerEntity.setEmail(email);
        String phonenumber = "test";
        customerEntity.setPhonenumber(phonenumber);
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
        when(customerRepository.findById(id)).thenReturn(Optional.of(customerEntity));

        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> customerService.getCustomerById(123L));

        CustomerEntity customerById = customerService.getCustomerById(id);

        assertThat(customerById.getId()).isEqualTo(id);
        assertThat(customerById.getEmail()).isEqualTo(customerEntity.getEmail());
        assertThat(customerById.getPhonenumber()).isEqualTo(customerEntity.getPhonenumber());
    }

    @Test
    void deleteCustomer() throws Exception {
        Long id = 1L;
        CustomerEntity customer = new CustomerEntity();
        customer.setId(id);

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(customerRepository.findById(123L)).thenReturn(Optional.empty());

        customerService.deleteCustomer(id);

        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> customerService.deleteCustomer(123L));
    }
}