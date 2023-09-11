package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Product>> create(@RequestBody List<Product> products){
        return new ResponseEntity<>(productService.createProducts(products), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> allProducts(){
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") Long id){
        var optionalProduct = productService.findProductById(id);
        return optionalProduct
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> update(@PathVariable("productId") Long id,
                                          @RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable("productId") Long id){
        productService.removeProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
