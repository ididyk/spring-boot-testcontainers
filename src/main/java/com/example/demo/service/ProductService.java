package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(ProductDTO productDTO){
        var product = ProductDTO.toEntity(productDTO);
        return productRepository.save(product);
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }


    public List<Product> createProducts(List<Product> products){
        return productRepository.saveAll(products);
    }


    public Optional<Product> findProductById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Product updateProduct(Long id, ProductDTO productDTO){
        var optionalProduct = findProductById(id);
        if (optionalProduct.isPresent()) {
            var product = optionalProduct.get();
            product.setPrice(productDTO.price());
            product.setName(productDTO.name());
            product.setSku(productDTO.sku());
            return productRepository.save(product);
        }
        return null;
    }

    public void removeProduct(Long id){
        var optionalProduct = findProductById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

}
