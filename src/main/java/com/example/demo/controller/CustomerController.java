package com.example.demo.controller;

import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.MessageResponse;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.exception.CustomerAlreadyExistException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerServiceImpl customerService;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody CustomerDto customerDto) {
        try {
            customerService.addNewCustomer(customerDto);
            return ResponseEntity.ok(new MessageResponse("Customer CREATED"));
        } catch (CustomerAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> updateCustomer(@PathVariable String email, @RequestBody CustomerDto customerDto) {
        try {
            customerService.updateCustomer(email, customerDto);
            return ResponseEntity.ok(new MessageResponse("Customer UPDATED"));
        } catch (CustomerAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("all-customers")
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<CustomerEntity> customers = customerService.getAll();
            List<CustomerDto> result = new ArrayList<>();
            for (CustomerEntity customer : customers) {
                CustomerDto customerDto = CustomerDto.fromCustomer(customer);
                result.add(customerDto);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        try {
            CustomerEntity customer = customerService.getCustomerById(Long.valueOf(id));
            CustomerDto result = CustomerDto.fromCustomer(customer);
            return ResponseEntity.ok(result);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("by-email/{email}")
    public ResponseEntity<?> getCustomerByEmail(@PathVariable String email) {
        try {
            CustomerEntity customer = customerService.getCustomerByEmail(email);
            CustomerDto result = CustomerDto.fromCustomer(customer);
            return ResponseEntity.ok(result);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("by-phonenumber/{phonenumber}")
    public ResponseEntity<?> getCustomerByPhonenumber(@PathVariable String phonenumber) {
        try {
            CustomerEntity customer = customerService.getCustomerByPhonenumber(phonenumber);
            CustomerDto result = CustomerDto.fromCustomer(customer);
            return ResponseEntity.ok(result);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        try {
            customerService.deleteCustomer(Long.valueOf(id));
            return ResponseEntity.ok("Customer with ID "+ id +" deleted");
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }
}
