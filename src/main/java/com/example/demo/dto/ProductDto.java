package com.example.demo.dto;

import com.example.demo.entity.ProductCategoryEntity;
import com.example.demo.entity.ProductEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Boolean availability;
    private Set<String> categories = new HashSet<>();

    public static ProductDto fromProduct(ProductEntity product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setAvailability(product.getAvailability());
        Set<String> tmp = new HashSet<>();
        for (ProductCategoryEntity category : product.getCategories()) {
            tmp.add(category.getTitle());
        }
        productDto.setCategories(tmp);
        return productDto;
    }
}