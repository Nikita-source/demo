package com.example.demo.controller;

import com.example.demo.dto.MessageResponse;
import com.example.demo.entity.ProductCategoryEntity;
import com.example.demo.exception.ProductCategoryAlreadyExistException;
import com.example.demo.exception.ProductCategoryNotFoundException;
import com.example.demo.service.impl.ProductCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/productcategory")
public class ProductCategoryController {
    private final ProductCategoryServiceImpl productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryServiceImpl productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody String title) {
        try {
            productCategoryService.addNewCategory(title);
            return ResponseEntity.ok(new MessageResponse("Category CREATED"));
        } catch (ProductCategoryAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("all-categories")
    public ResponseEntity<?> getAllCategories() {
        try {
            List<ProductCategoryEntity> categories = productCategoryService.getAll();
            List<String> result = new ArrayList<>();
            for (ProductCategoryEntity category : categories) {
                result.add(category.getTitle());
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        try {
            productCategoryService.deleteCategory(Long.valueOf(id));
            return ResponseEntity.ok("Category with ID " + id + " deleted");
        } catch (ProductCategoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }
}
