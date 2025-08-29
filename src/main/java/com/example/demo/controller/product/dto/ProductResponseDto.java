package com.example.demo.controller.product.dto;

import com.example.demo.repository.product.entity.Product;
import com.example.demo.repository.product.entity.ProductImage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponseDto {
    private Integer id;
//    private Integer userId;
    private Integer baseId;
    private List<ImageSimpleDto> ImageUrls;

    private String name;

    public static ProductResponseDto from(Product entity) {
        return new ProductResponseDto(
                entity.getId(),
//                entity.getUser().getId(),
                entity.getBase().getId(),
                entity.getProductImages().stream()
                        .map(ProductImage::getImage)
                        .map(ImageSimpleDto::from)
                        .toList(),
                entity.getName()
        );
    }
}
