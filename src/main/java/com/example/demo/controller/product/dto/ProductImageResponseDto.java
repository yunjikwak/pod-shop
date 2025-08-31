package com.example.demo.controller.product.dto;

import com.example.demo.repository.product.entity.ProductImage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductImageResponseDto {
    private Integer id;
    private String imageUrl;
    private ProductImage.ProductImageStatus status;
    private List<ReviewHistoryResponseDto> reviewHistoryResponseDtos;

    public static ProductImageResponseDto from(ProductImage entity) {
        return new ProductImageResponseDto(
                entity.getId(),
                entity.getImage().getUrl(),
                entity.getStatus(),
                entity.getReviewHistories().stream()
                        .map(ReviewHistoryResponseDto::from)
                        .toList()
        );
    }

}
