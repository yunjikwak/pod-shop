package com.example.demo.controller.product;

import com.example.demo.controller.product.dto.ProductCreateRequestDto;
import com.example.demo.controller.product.dto.ProductResponseDto;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductController {
    ProductService productService;

    @PostMapping("")
    public ResponseEntity<ProductResponseDto> create(@RequestBody @Valid ProductCreateRequestDto request) {
        ProductResponseDto product = productService.save(request);
        return ResponseEntity.ok(product);
    }
}
