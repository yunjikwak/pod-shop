package com.example.demo.controller.product.dto;

import com.example.demo.repository.product.entity.ReviewHistory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewHistoryResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private ReviewHistory.ReviewHistoryStatus status;

    public static ReviewHistoryResponseDto from(ReviewHistory entity) {
        return new ReviewHistoryResponseDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getStatus()
        );
    }
}
