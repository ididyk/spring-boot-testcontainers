package com.example.demo.dto;

import com.example.demo.entity.Product;

public record ProductDTO(
        String name,
        String sku,
        Double price
) {

    public static Product toEntity(ProductDTO productDTO){
       var product =  new Product();
       product.setName(productDTO.name());
       product.setSku(productDTO.sku());
       product.setPrice(productDTO.price());
       return product;
    }
}
