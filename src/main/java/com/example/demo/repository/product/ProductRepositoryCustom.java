package com.example.demo.repository.product;

import com.example.demo.repository.product.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findAllById(Integer id);
}
