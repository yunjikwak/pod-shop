package com.example.demo.repository.product;

import com.example.demo.repository.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findAllById(Integer id);
    Page<Product> findByStatus(Product.ProductStatus status, Pageable pageable);
}
