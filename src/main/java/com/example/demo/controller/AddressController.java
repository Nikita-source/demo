package com.example.demo.controller;

import com.example.demo.dto.MessageResponse;
import com.example.demo.entity.AddressEntity;
import com.example.demo.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @PostMapping
    public ResponseEntity<?> addAddress(@RequestBody AddressEntity address) {
        try {
            if (addressRepository.findByAddress(address.getAddress()) != null) {
                return ResponseEntity.badRequest().body("Address already exist");
            }
            addressRepository.save(address);
            return ResponseEntity.ok(new MessageResponse("Address CREATED"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("all-addresses")
    public ResponseEntity<?> getAllAddresses() {
        try {
            List<AddressEntity> addresses = addressRepository.findAll();
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable String id) {
        try {
            addressRepository.findById(Long.valueOf(id)).
                    orElseThrow(() -> new RuntimeException("Address with this ID not found"));
            addressRepository.deleteById(Long.valueOf(id));
            return ResponseEntity.ok("Address with ID " + id + " deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }
}
