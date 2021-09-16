package com.example.demo.controller;

import com.example.demo.dto.MessageResponse;
import com.example.demo.dto.ProductDto;
import com.example.demo.entity.ProductEntity;
import com.example.demo.exception.ProductAlreadyExistException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductEntity product) {
        try {
            productService.addNewProduct(product);
            return ResponseEntity.ok(new MessageResponse("Product CREATED"));
        } catch (ProductAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @PutMapping("/{title}")
    public ResponseEntity<?> updateProduct(@PathVariable String title, @RequestBody ProductEntity productEntity) {
        try {
            productService.updateProduct(title, productEntity);
            return ResponseEntity.ok("Product UPDATED");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProductAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("all-products")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<ProductEntity> products = productService.getAll();
            List<ProductDto> result = new ArrayList<>();
            for (ProductEntity product : products) {
                ProductDto productDto = ProductDto.fromProduct(product);
                result.add(productDto);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        try {
            ProductEntity product = productService.getProductById(Long.valueOf(id));
            ProductDto productDto = ProductDto.fromProduct(product);
            return ResponseEntity.ok(productDto);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("by-title/{title}")
    public ResponseEntity<?> getCustomerByEmail(@PathVariable String title) {
        try {
            ProductEntity product = productService.getProductByTitle(title);
            ProductDto productDto = ProductDto.fromProduct(product);
            return ResponseEntity.ok(productDto);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        try {
            productService.deleteProduct(Long.valueOf(id));
            return ResponseEntity.ok("Product with ID " + id + " deleted");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }
}
