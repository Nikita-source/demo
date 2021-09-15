package com.example.demo.controller;

import com.example.demo.dto.MessageResponse;
import com.example.demo.entity.SupplyEntity;
import com.example.demo.exception.SupplyException;
import com.example.demo.service.impl.SupplyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/supply")
public class SupplyController {
    private final SupplyServiceImpl supplyService;

    @Autowired
    public SupplyController(SupplyServiceImpl supplyService) {
        this.supplyService = supplyService;
    }

    @PostMapping
    public ResponseEntity<?> addSupply(@RequestBody SupplyEntity supply) {
        try {
            supplyService.addNewSupply(supply);
            return ResponseEntity.ok(new MessageResponse("Product CREATED"));
        } catch (SupplyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("all-supplies")
    public ResponseEntity<?> getAllSupplies() {
        try {
            List<SupplyEntity> supplies = supplyService.getAll();
            /*List<ProductDto> result = new ArrayList<>();
            for (ProductEntity product: products) {
                ProductDto productDto = ProductDto.fromProduct(product);
                result.add(productDto);
            }
            return ResponseEntity.ok(result);*/
            return ResponseEntity.ok(supplies);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }
}
